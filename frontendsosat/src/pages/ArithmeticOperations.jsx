import React, { useState } from "react"
import useFileHandler from "../components/useFileHandler/useFileHandler.jsx"
import useTable from "../components/useTable/useTable.jsx"

const ArithmeticOperations = () => {
  const [operation, setOperation] = useState("+")
  const [selectedPoints, setSelectedPoints] = useState()
  const server = "http://localhost:8080/api"

  const {
    handleFileUpload,
    showError,
    openModal,
    Modal,
    Messages
  } = useFileHandler("http://localhost:8080/api")
  const {
    points: points1,
    setPoints: setPoints1,
    renderTable: renderTable1
  } = useTable([{ x: "", y: "" }])
  const {
    points: points2,
    setPoints: setPoints2,
    renderTable: renderTable2
  } = useTable([{ x: "", y: "" }])
  const { points: result, setPoints: setResult } = useTable([{ x: "", y: "" }])

  const performOperation = () => {
    const nonEmptyPoints1 = points1.slice(0, -1)
    const nonEmptyPoints2 = points2.slice(0, -1)
    if (
      nonEmptyPoints1.some(point => point.x === "" || point.y === "") ||
      nonEmptyPoints2.some(point => point.x === "" || point.y === "")
    ) {
      showError("Все строки должны быть полностью заполнены!")
    } else if (nonEmptyPoints1.length < 2 || nonEmptyPoints2.length < 2) {
      showError("Должно быть не менее двух полных строк!")
    } else if (
      !nonEmptyPoints1.every(
        (point, i) => i === 0 || point.x > nonEmptyPoints1[i - 1].x
      ) ||
      !nonEmptyPoints2.every(
        (point, i) => i === 0 || point.x > nonEmptyPoints2[i - 1].x
      )
    ) {
      showError("Значения X должны быть в возрастающем порядке!")
    } else if (nonEmptyPoints1.length !== nonEmptyPoints2.length) {
      showError("Таблицы должны иметь одинаковое количество точек!")
    } else if (
      !nonEmptyPoints1.every(
        (point, index) => point.x === nonEmptyPoints2[index].x
      )
    ) {
      showError("Значения X в обеих таблицах должны совпадать!")
    } else {
      const newResult = points1.map((point1, index) => {
        const point2 = points2[index]
        let y
        switch (operation) {
          case "+":
            y = point1.y + point2.y
            break
          case "-":
            y = point1.y - point2.y
            break
          case "*":
            y = point1.y * point2.y
            break
          case "/":
            y = point2.y !== 0 ? point1.y / point2.y : 0
            break
          default:
            y = 0
        }
        return { x: point1.x, y }
      })

      if (
        newResult.length > 1 &&
        (newResult[newResult.length - 1].x === "" ||
          newResult[newResult.length - 1].y === "")
      ) {
        newResult.pop()
      }

      setResult(newResult)
    }
  }

  return (
    <div style={{display: "flex", 
      flexDirection: "column"}}>
      <Messages />
      <h1>
        Арифметические операции
      </h1>
      <div>
        <div style={{display:"flex",flexDirection:"column",justifyItems:"center"}}>
          <div>
            <button
              onClick={() => document.getElementById("fileInputFirst")?.click()}
              style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
            >
              Загрузить таблицу
            </button>
            <input
              type="file"
              id="fileInputFirst"
              style={{ display: "none" }}
              accept=".json,.bin,.xml"
              onChange={e => handleFileUpload(e, setPoints1)}
            />
            <button
              onClick={() => {
                setSelectedPoints(1)
                openModal(points1)
              }}
              style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
            >
              Сохранить таблицу
            </button>
          </div>
          {renderTable1()}
        </div>

        <div style={{display:"flex",flexDirection:"column",justifySelf:"center",width:"33.333333%"}}>
          <button
           style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
            onClick={() =>
              setOperation(prev =>
                prev === "+"
                  ? "-"
                  : prev === "-"
                  ? "*"
                  : prev === "*"
                  ? "/"
                  : "+"
              )
            }
          >
            {operation}
          </button>
        </div>

        <div>
          <div>
            <button
             style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
              onClick={() =>
                document.getElementById("fileInputSecond")?.click()
              }
            >
              Загрузить таблицу
            </button>
            <input
              type="file"
              id="fileInputSecond"
              style={{ display: "none" }}
              accept=".json,.bin,.xml"
              onChange={e => handleFileUpload(e, setPoints2)}
            />
            <button
             style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
              onClick={() => {
                setSelectedPoints(2)
                openModal(points2)
              }}
            >
              Сохранить таблицу
            </button>
          </div>
          {renderTable2()}
        </div>

        <div style={{display:"flex",flexDirection:"column",justifySelf:"center",width:"33.333333%"}}>
          <span style={{color:"white"}}>=</span>
        </div>

        <div style={{display:"flex",flexDirection:"column",justifySelf:"center",width:"33.333333%"}}>
          <div>
            <button
              style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
              onClick={performOperation}
            >
              Выполнить
            </button>
            <button
               style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
              onClick={() => {
                setSelectedPoints(3)
                openModal(result)
              }}
            >
              Сохранить таблицу
            </button>
          </div>

          <div
            style={{maxHeight: "450px",background:"orange",borderRadius:"4px",justifyItems:"center"}}
          >
            <div
              style={{
                maxHeight: points2.length > 10 ? '400px' : 'auto',
            }}
            >
              <table style={{columnGap:"20px",border:"1px solid",borderCollapse:"collapse",borderSpacing:"5px",width:"100px"}}>
                <thead style={{columnGap:"40px",border:"1px solid",width:"40px"}}>
                  <tr>
                    <th style={{color:"black",border:"1px solid"}}>
                      #
                    </th>
                    <th style={{color:"black",border:"1px solid"}}>
                      X
                    </th>
                    <th style={{color:"black",border:"1px solid"}}>
                      Y
                    </th>
                  </tr>
                </thead>
                <tbody style={{color:"black",border:"1px solid"}}>
                  {
                  result.map((point, index) => (
                    <tr
                      key={index}
                      style={{border:"1px solid"}}
                    >
                      <td style={{textAlign:"center"}}>
                        {index + 1}
                      </td>
                      <td style={{color:"black",border:"1px solid"}}>
                        {point.x}
                      </td>
                      <td style={{color:"black",border:"1px solid"}}>
                        {point.y}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <Modal
        points={
          selectedPoints === 1
            ? points1
            : selectedPoints === 2
            ? points2
            : result
        }
      />
    </div>
  )
}

export default ArithmeticOperations
