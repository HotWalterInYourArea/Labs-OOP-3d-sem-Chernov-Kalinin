import React, { useState } from "react"
import useFileHandler from "../components/useFileHandler/useFileHandler.jsx"
import axios from "axios"
import useTable from "../components/useTable/useTable.jsx"

const DifferentiationIntegration = () => {
  const [operation, setOperation] = useState("derivative")
  const [result, setResult] = useState(null)
  const [threads, setThreads] = useState("")
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

  const performOperation = async () => {
    if (!points1 || points1.length < 2) {
      showError(
        "Пожалуйста, загрузите функцию или добавьте хотя бы два значения."
      )
      return
    }
    const xValues = points1.map(point => point.x).filter(x => x !== "")
    const yValues = points1.map(point => point.y).filter(y => y !== "")

    try {
      if (operation === "derivative") {
        const response = await axios.post(`${server}/operations/doDifferential`, {
          xValues,
          yValues
        })
        setPoints2(
          response.data.xValues.map((x, i) => ({
            x,
            y: response.data.yValues[i]
          }))
        )
        setResult(" ")
      } else if (operation === "integral") {
        if (!threads) {
          showError("Пожалуйста, укажите количество потоков.")
          return
        }
        const response = await axios.post(`${server}/operations/doIntegral/${threads}`, {
          xValues,
          yValues
        })
        const integralResult = JSON.parse(response.data)
        setResult(`${integralResult}`)
      }
    } catch (error) {
      console.error("Ошибка при выполнении операции:", error)
      showError("Ошибка при выполнении операции.")
    }
  }

  return (
    <div>
      <Messages />
      <h1>
        Дифференцирование & Интегрирование
      </h1>
      <div>
        <div>
          <div>
            <button
              onClick={() => document.getElementById("fileInput")?.click()}
              style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer"}}
            >
              Загрузить таблицу
            </button>
            <input
              type="file"
              id="fileInput"
              style={{ display: "none" }}
              accept=".json,.bin,.xml"
              onChange={e => handleFileUpload(e, setPoints1)}
            />
            <button
              onClick={() => {
                setSelectedPoints(1)
                openModal(points1)
              }}
              style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer"}}
            >
              Сохранить таблицу
            </button>
          </div>
          {renderTable1()}
        </div>
        <div style={{margin:"10px"}}>
          <div>
            <label style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:500,margin:"10px"}}>
              Выберите операцию:
              </label>
            <select
              value={operation}
              onChange={e => setOperation(e.target.value)}
            >
              <option value="derivative">
                Производная
              </option>
              <option value="integral">
                Определённый интеграл
              </option>
            </select>
          </div>
          {operation === "integral" && (
            <div style={{margin:"10px"}}>
              <label style={{background:"orange",borderRadius:"4px",padding:"0,5px",border:"none",fontWeight:500,margin:"20px"}}>
                Количество потоков для исполнения:
              </label>
              <input
                type="number"
                value={threads}
                onChange={e => setThreads(parseInt(e.target.value) || "")}
                min="1"
              />
            </div>
          )}
          <button
            onClick={performOperation}
            style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer"}}
          >
            Выполнить
          </button>
          {result && (
            <div style={{color:"white"}}>
              {operation === "integral" ? (
                <div>
                  <strong style={{color:"white"}}>Результат интеграла:</strong> {result}
                </div>
              ) : (
                operation === "derivative" &&
                points2.length > 0 && (
                  <div>
                    {renderTable2()}
                    <button
                      onClick={() => {
                        setSelectedPoints(2)
                        openModal(points2)
                      }}
                    >
                      Сохранить таблицу производной
                    </button>
                  </div>
                )
              )}
            </div>
          )}
        </div>

        <Modal points={selectedPoints === 1 ? points1 : points2} />
      </div>
    </div>
  )
}

export default DifferentiationIntegration
