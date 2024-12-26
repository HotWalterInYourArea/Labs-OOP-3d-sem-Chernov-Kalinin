import React, {useCallback, useState} from 'react';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import { saveAs } from 'file-saver';
import axios from 'axios';
import {XCircleIcon} from "lucide-react";

const useFileHandler = (server) => {
    const [errorMessage, setErrorMessage] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const [isCloseButtonVisible, setIsCloseButtonVisible] = useState(false);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = useCallback((points) => {
        const nonEmptyPoints = points.filter(point => point.x !== '' && point.y !== '');

        if (nonEmptyPoints.some(point => point.x === '' || point.y === '')) {
            showError('Все строки должны быть полностью заполнены!');
        } else if (nonEmptyPoints.length < 2) {
            showError('Должно быть не менее двух полных строк!');
        } else if (!nonEmptyPoints.every((point, i) => i === 0 || point.x > nonEmptyPoints[i - 1].x)) {
            showError('Значения X должны быть в возрастающем порядке!');
        } else {
            setIsModalOpen(true);
        }
    }, []);

    const closeModal = useCallback(() => {
        setIsModalOpen(false);
    }, []);

    const showError = (message) => {
        setErrorMessage(message);
        setIsCloseButtonVisible(false);
        setTimeout(() => setIsCloseButtonVisible(true), 1500);
    };

    const closeError = () => {
        setErrorMessage(null);
        setIsCloseButtonVisible(false);
    };

    const showSuccess = (message) => {
        setErrorMessage(null);
        setSuccessMessage(message);
        setTimeout(() => setSuccessMessage(null), 1500);
    };

    const handleFileUpload = async (event, setPoints) => {
        const file = event.target.files?.[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = async () => {
                const result = reader.result;

                if (file.type === 'application/json' || file.name.endsWith('.json')) {
                    try {
                        const loadedPoints = JSON.parse(result);

                        if (!validateFunctionData(loadedPoints)) {
                            return;
                        }

                        setPoints(loadedPoints.xValues.map((x, i) => ({ x, y: loadedPoints.yValues[i] })).concat({ x: '', y: '' }));
                        showSuccess('Файл успешно загружен.');
                    } catch (e) {
                        console.error('Ошибка при обработке JSON:', e);
                        showError('Ошибка при обработке JSON: некорректный формат данных.');
                    }
                } else if (file.type === 'application/xml' || file.name.endsWith('.xml')) {
                    try {
                        const response = await axios.post(`${server}/converter/convertFromXML`, result,{
                            headers: {
                                'Content-Type': 'application/xml',
                            },
                        });

                        if (!validateFunctionData(response.data)) {
                            return;
                        }

                        setPoints(response.data.xValues.map((x, i) => ({ x, y: response.data.yValues[i] })).concat({ x: '', y: '' }));
                        showSuccess('Файл успешно загружен.');
                    } catch (e) {
                        console.error('Ошибка при обработке XML:', e);
                        showError('Ошибка при обработке XML: некорректный формат данных.');
                    }
                } else {
                    try {
                        const response = await axios.post(`${server}/converter/convertFromBLOB`,result, {
                            headers: {
                                'Content-Type': 'application/octet-stream',
                            },
                        });

                        if (!validateFunctionData(response.data)) {
                            return;
                        }

                        setPoints(response.data.xValues.map((x, i) => ({ x, y: response.data.yValues[i] })).concat({ x: '', y: '' }));
                        showSuccess('Файл успешно загружен.');
                    } catch (e) {
                        console.error('Ошибка при обработке BLOB:', e);
                        showError('Ошибка при обработке BLOB: некорректный формат данных.');
                    }
                }
            };

            if (file.type === 'application/json' || file.name.endsWith('.json')) {
                reader.readAsText(file);
            } else if (file.type === 'application/xml' || file.name.endsWith('.xml')) {
                reader.readAsText(file);
            } else {
                reader.readAsArrayBuffer(file);
            }

            event.target.value = '';
        }
    };

    const handleSave = async (format, points) => {
        const xValues = points.map(point => point.x).filter(x => x !== '');
        const yValues = points.map(point => point.y).filter(y => y !== '');

        closeModal()

        try {
            let response;
            let fileName;
            let fileType;

            switch (format) {
                case 'Byte':
                    response = await axios.post(
                        `${server}/tabulatedFunctions/createTabulatedFunctionWithTableByte`,
                        { xValues, yValues },
                        {
                            responseType: 'arraybuffer',
                            withCredentials: true
                        }
                    );
                    fileName = 'function.bin';
                    fileType = 'application/octet-stream';
                    break;
                case 'JSON':
                    response = await axios.post(
                        `${server}/tabulatedFunctions/createTabulatedFunctionWithTableJSON`,
                        { xValues, yValues },
                        {
                            withCredentials: true
                        }
                    );
                    fileName = 'function.json';
                    fileType = 'application/json';
                    break;
                case 'XML':
                    response = await axios.post(
                        `${server}/tabulatedFunctions/createTabulatedFunctionWithTableXML`,
                        { xValues, yValues },
                        {
                            withCredentials: true
                        }
                    );
                    fileName = 'function.xml';
                    fileType = 'application/xml';
                    break;
                default:
                    console.error('Unknown format:', format);
                    return;
            }

            const fileData =
                format === 'JSON' ? JSON.stringify(response.data, null, 2) : response.data;
            const blob = new Blob([fileData], { type: fileType });
            saveAs(blob, fileName);

            showSuccess('Функция успешно сохранена!');
        } catch (error) {
            console.error('Error saving function:', error);
            showError('Ошибка при сохранении функций.');
        }
    };

    const validateFunctionData = (data) => {
        const validClasses = [
            'functions.LinkedListTabulatedFunction',
            'functions.ArrayTabulatedFunction',
        ];
        if (!validClasses.includes(data['@class'])) {
            showError('Ошибка: некорректное значение "@class".');
            return false;
        }

        if (data.xValues.length !== data.yValues.length) {
            showError('Ошибка: количество значений в массивах xValues и yValues не совпадает.');
            return false;
        }

        for (let i = 0; i < data.xValues.length - 1; i++) {
            if (data.xValues[i] >= data.xValues[i + 1]) {
                showError('Ошибка: значения в xValues не идут строго по возрастанию.');
                return false;
            }
        }

        if (data.count !== data.xValues.length) {
            showError('Ошибка: значение "count" не совпадает с количеством пар xValues-yValues.');
            return false;
        }

        return true;
    };

    const Modal = ({ points }) => (
        isModalOpen && (
            <div className='modal'>
                <div className='modal-content'>
                    <h2>Выберите тип сохранения</h2>
                    <div>
                        <button
                            onClick={() => handleSave('Byte', points)}
                        >
                            Byte
                        </button>
                        <button
                            onClick={() => handleSave('JSON', points)}
                        >
                            JSON
                        </button>
                        <button
                            onClick={() => handleSave('XML', points)}
                        >
                            XML
                        </button>
                    </div>
                    <button
                        onClick={closeModal}
                    >
                        Отмена
                    </button>
                </div>
            </div>
        )
    );

    const Messages = () => (
        <div>
            {successMessage && (
                <div>
                    <div >
                        <span>{successMessage}</span>
                    </div>
                </div>
            )}
            {errorMessage && (
                <div>
                    <div>
                        <span style={{background:"orange",borderRadius:"4px",padding:"1px",border:"none",fontWeight:800,margin:"10px"}}>{errorMessage}</span>
                        <button
                        style={{cursor:"pointer"}}
                            onClick={closeError}
                        >
                            <XCircleIcon/>
                        </button>
                    </div>
                </div>
            )}
        </div>
    );


    return {
        handleFileUpload,
        handleSave,
        errorMessage,
        successMessage,
        isCloseButtonVisible,
        showError,
        closeError,
        showSuccess,
        openModal,
        closeModal,
        Modal,
        Messages,
    };
};

export default useFileHandler;