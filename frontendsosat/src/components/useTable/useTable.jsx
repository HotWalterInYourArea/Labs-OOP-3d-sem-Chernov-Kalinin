import { useState, useCallback } from 'react';

const useTable = (initialPoints) => {
    const [points, setPoints] = useState(initialPoints);
    const handlePointChange = useCallback((index, axis, value) => {
        const newValue = value === '' ? '' : parseFloat(value);
        const updatedPoints = [...points];
        updatedPoints[index] = {
            ...updatedPoints[index],
            [axis]: isNaN(newValue) ? '' : newValue,
        };

        if (index === points.length - 1 && (updatedPoints[index].x !== '' || updatedPoints[index].y !== '')) {
            updatedPoints.push({ x: '', y: '' });
        }

        const filteredPoints = updatedPoints.filter(
            (point, i) => i === updatedPoints.length - 1 || point.x !== '' || point.y !== ''
        );

        setPoints(filteredPoints);
    }, [points]);

    const sortColumnX = useCallback(() => {
        const sortedPoints = [...points].sort((a, b) => {
            if (a.x === '' || isNaN(a.x)) return 1;
            if (b.x === '' || isNaN(b.x)) return -1;
            return a.x - b.x;
        });
        setPoints(sortedPoints);
    }, [points]);

    const removeDuplicates = useCallback(() => {
        const uniquePoints = points.reduce((res, point) => {
            if (point.x === '') {
                res.push(point);
            } else {
                const isDuplicate = res.some(p => p.x === point.x);
                if (!isDuplicate) {
                    res.push(point);
                }
            }
            return res;
        }, []);

        setPoints(uniquePoints);
    }, [points]);
    const isSorted = useCallback((arr) => {
        for (let i = 1; i < arr.length; i++) {
            if (arr[i - 1] !== '' && arr[i] !== '' && parseFloat(arr[i - 1]) > parseFloat(arr[i])) {
                return false;
            }
        }
        return true;
    }, []);

    const hasDuplicates = useCallback((arr) => {
        const nonEmptyValues = arr.filter(value => value !== '');
        const uniqueValues = new Set(nonEmptyValues);
        return uniqueValues.size !== nonEmptyValues.length;
    }, []);
    const xValues = points.map(point => point.x);
    const showSortButton = !isSorted(xValues);
    const showRemoveDuplicatesButton = isSorted(xValues) && hasDuplicates(xValues);

    const renderTable = () => (
        <div style={{ maxHeight: '450px', alignContent: 'center' }}>
            <div
                style={{
                    maxHeight: points.length > 10 ? '400px' : 'auto',
                    alignContent: 'center'
                }}
            >
                <table style={{justifySelf:'center',border:"1px solid"}}>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th style={{color:"white"}}>
                            X
                            {showSortButton && (
                                <button
                                    onClick={sortColumnX}
                                >
                                    Сортировать
                                </button>
                            )}
                            {showRemoveDuplicatesButton && (
                                <button
                                    onClick={removeDuplicates}
                                >
                                    Удалить дубликаты
                                </button>
                            )}
                        </th>
                        <th style={{color:"white"}}>Y</th>
                    </tr>
                    </thead>
                    <tbody style={{color:'white'}}>

                    {
                        points.map((point, index) => (
                        <tr
                            key={index}
                        >
                            <td>
                                {index === points.length - 1 ? '#' : index + 1}
                            </td>
                            <td>
                                <input
                                    type="number"
                                    step="any"
                                    value={point.x}
                                    onChange={(e) => handlePointChange(index, 'x', e.target.value)}
                                    onWheel={(e) => e.currentTarget.blur()}
                                    style={{ appearance: 'textfield' }}
                                />
                            </td>
                            <td>
                                <input
                                    type="number"
                                    step="any"
                                    value={point.y}
                                    onChange={(e) => handlePointChange(index, 'y', e.target.value)}
                                    onWheel={(e) => e.currentTarget.blur()}
                                    style={{ appearance: 'textfield' }}
                                />
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );

    return {
        points,
        setPoints,
        renderTable,
    };
};

export default useTable;