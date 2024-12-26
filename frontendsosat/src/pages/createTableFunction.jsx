import React, { useEffect, useState } from 'react';
import axios from 'axios';
import useTable from '../components//useTable/useTable';
import useFileHandler from "../components/useFileHandler/useFileHandler";

const CreateTableFunction = () => {
    const [functionType, setFunctionType] = useState('Нулевая функция');
    const [xRange, setXRange] = useState({ start: '', end: '' });
    const [pointCount, setPointCount] = useState('');
    const [availableFunctions, setAvailableFunctions] = useState([]);
    const server = 'http://localhost:8080/api';

    const { showError, openModal, Modal, Messages } = useFileHandler(server);
    const { points, setPoints, renderTable } = useTable([{ x: '', y: '' }]);
    const array1=sessionStorage.getItem("functionNames").split(",");
    const array2=sessionStorage.getItem("functions").split(",")
    const map = new Map;
    let i=0;
    while(i<array1.length){
        map.set(array1[i],array2[i])
        i++;
    }
    useEffect(() => {
        const fetchFunctions = async () => {
            try {
                setAvailableFunctions(array1);
            } catch (error) {
                console.error('Error fetching functions:', error);
                showError('Ошибка при загрузке доступных функций.');
            }
        };

        fetchFunctions();
    }, []);

    const generatePoints = async () => {
        const start = parseFloat(xRange.start);
        const end = parseFloat(xRange.end);
        const count = parseInt(pointCount, 10);

        if (start > end) {
            showError('Начальное значение диапазона не может быть больше конечного!');
            return;
        }
        if (!Number.isInteger(count)) {
            showError('Количество точек должно быть целым числом!');
            return;
        }
        if (count < 2) {
            showError('Количество точек должно быть не менее двух!');
            return;
        }

        try {
            const response = await axios.post(
                `${server}/tabulatedFunctions/createTabulatedFunctionWithFunctionJSON`,
                {
                    functionName: map.get(functionType),
                    from: start,
                    to: end,
                    amountOfPoints: count,
                },
                {
                    withCredentials: true,
                }
            );

            const { xValues, yValues } = response.data;
            const generatedPoints = xValues.map((x , index) => ({
                x,
                y: yValues[index],
            }));

            setPoints([...generatedPoints, { x: '', y: '' }]);
        } catch (error) {
            console.error('Error generating points:', error);
            showError('Ошибка при генерации точек.');
        }
    };

    const clearPoints = () => {
        setPoints([{ x: '', y: '' }]);
    };

    return (
        <div>
            <Messages/>
            <h1>Создание табличной функции</h1>
            <div>
                <div>
                    <h2>Ввод точек вручную</h2>
                    {renderTable()}
                    <button
                        onClick={() => openModal(points)}
                        style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px"}}
                    >
                        Сохранить таблицу
                    </button>
                    <button
                        onClick={clearPoints}
                        style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px"}}
                    >
                        Очистить
                    </button>
                </div>
                <div>
                    <h2>Генерация точек</h2>
                    <div>
                        <label style={{background:"orange",borderRadius:"4px",padding:"0.5px",border:"none",fontWeight:500,margin:"10px"}}>Базовая функция</label>
                        <select
                            value={functionType}
                            onChange={(e) => {setFunctionType(e.target.value)}}
                        >
                            {availableFunctions
                                .sort((a, b) => a.localeCompare(b))
                                .map((func, index) => (
                                    <option key={index} value={func}>
                                        {func}
                                    </option>
                                ))}
                        </select>
                    </div>
                    <div>
                        <label style={{background:"orange",borderRadius:"4px",padding:"0.5px",border:"none",fontWeight:500,margin:"10px"}}>Диапазон X</label>
                        <input
                            type="number"
                            step="any"
                            placeholder="Начало"
                            value={xRange.start}
                            onChange={(e) => setXRange({...xRange, start: e.target.value})}
                            style={{appearance: 'textfield'}}
                        />
                        <input
                            type="number"
                            step="any"
                            placeholder="Конец"
                            value={xRange.end}
                            onChange={(e) => setXRange({...xRange, end: e.target.value})}
                            style={{appearance: 'textfield'}}
                        />
                    </div>
                    <div style={{margin:"10px"}}>
                        <label style={{background:"orange",borderRadius:"4px",padding:"1px",border:"none",fontWeight:500,margin:"10px"}}>Количество точек</label>
                        <input
                            type="number"
                            step="any"
                            value={pointCount}
                            onChange={(e) => setPointCount(e.target.value)}
                            style={{appearance: 'textfield'}}
                        />
                    </div>
                    <div>
                        <button
                            style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px"}}
                            onClick={generatePoints}
                        >
                            Сгенерировать
                        </button>
                    </div>
                </div>
            </div>
            <Modal points={points}/>
        </div>
    )
};

export default CreateTableFunction;