import React, { useState } from "react"
import {
  Chart as ChartJS,
  LineElement,
  PointElement,
  LinearScale,
  Title,
  Tooltip,
  Legend,
  CategoryScale
} from "chart.js"
import { Line } from "react-chartjs-2"
import axios from "axios"
import useFileHandler from "../components/useFileHandler/useFileHandler.jsx"
import useTable from "../components/useTable/useTable.jsx"

ChartJS.register(
  LineElement,
  PointElement,
  LinearScale,
  CategoryScale,
  Title,
  Tooltip,
  Legend
)

const ViewGraphs = () => {
  const [data, setData] = useState({
    labels: [],
    datasets: [
      {
        label: "График",
        data: [],
        fill: false,
        borderColor: "rgb(75, 192, 192)",
        tension: 0.1
      }
    ]
  })
  const [xValue, setXValue] = useState("")
  const [fXValue, setFXValue] = useState(null)
  const server = "http://localhost:8080/api/operations"

  const {
    handleFileUpload,
    showError,
    openModal,
    Modal,
    Messages
  } = useFileHandler("http://localhost:8080/api")
  const { points, setPoints, renderTable } = useTable([{ x: "", y: "" }])

  const handleGraphGeneration = () => {
    const labels = points
      .filter(point => point.x !== "" && point.y !== "")
      .map(point => point.x)

    const dataPoints = points
      .filter(point => point.x !== "" && point.y !== "")
      .map(point => ({ x: point.x, y: point.y }))

    const nonEmptyPoints = points.slice(0, -1)
    if (nonEmptyPoints.some(point => point.x === "" || point.y === "")) {
      showError("Все строки должны быть полностью заполнены!")
    } else if (nonEmptyPoints.length < 2) {
      showError("Должно быть не менее двух полных строк!")
    } else if (
      !nonEmptyPoints.every(
        (point, i) => i === 0 || point.x > nonEmptyPoints[i - 1].x
      )
    ) {
      showError("Значения X должны быть в возрастающем порядке!")
    } else {
      setData({
        labels,
        datasets: [
          {
            label: "График",
            data: dataPoints,
            fill: false,
            borderColor: "rgb(75, 192, 192)",
            tension: 0.1
          }
        ]
      })
    }
  }

  const handleCalculateFX = async () => {
    const nonEmptyPoints = points.slice(0, -1)
    if (nonEmptyPoints.some(point => point.x === "" || point.y === "")) {
      showError("Все строки должны быть полностью заполнены!")
    } else if (nonEmptyPoints.length < 2) {
      showError("Должно быть не менее двух полных строк!")
    } else if (
      !nonEmptyPoints.every(
        (point, i) => i === 0 || point.x > nonEmptyPoints[i - 1].x
      )
    ) {
      showError("Значения X должны быть в возрастающем порядке!")
    } else {
      if (xValue === "") {
        setFXValue(null)
        return
      }

      const x = parseFloat(xValue)
      const xValues = nonEmptyPoints.map(point => point.x)
      const yValues = nonEmptyPoints.map(point => point.y)

      try {
        const response = await axios.post(`${server}/apply/${x}`, {
          xValues,
          yValues
        })

        const result = JSON.parse(response.data)
        setFXValue(`f(${x}) = ${result}`)
        console.log(response.data)
      } catch (error) {
        console.error("Ошибка при вычислении f(X):", error)
        showError("Ошибка при вычислении f(X).")
      }
    }
  }

  return (
    <div>
      <Messages />
      <h1 style={{color:"white"}}>
        Исследование табличной функции
      </h1>
      <div>
        <div>
          <div style={{color:"white"}}>
            <button
            style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
              onClick={() => document.getElementById("fileInput")?.click()}
            >
              Загрузить таблицу
            </button>
            <input
              type="file"
              id="fileInput"
              style={{ display: "none" }}
              accept=".json,.bin,.xml"
              onChange={e => handleFileUpload(e, setPoints)}
            />
            <button
            style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
              onClick={() => openModal(points)}
            >
              Сохранить таблицу
            </button>
          </div>
          <div style={{flex: "110%" }}>
            <label style={{background:"orange",borderRadius:"4px",padding:"1px",border:"none",fontWeight:500,margin:"10px"}}>
              Введите X для вычисления f(X):
            </label>
            <input
              type="number"
              step="any"
              value={xValue}
              onChange={e => setXValue(e.target.value)}
            />
            <div>
              <button
              style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
                onClick={handleCalculateFX}
              >
                Вычислить f(X)
              </button>
              {fXValue !== null && (
                <div style={{color:"white"}}>f(X) = {fXValue}</div>
              )}
            </div>
          </div>
        </div>
        {renderTable()}
        <button
          onClick={handleGraphGeneration}
          style={{background:"orange",borderRadius:"4px",padding:"3px",border:"none",fontWeight:1000,margin:"10px",cursor:"pointer",width:"auto",justifySelf:"center"}}
        >
          Построить график
        </button>
        <div style={{marginBottom:"1rem",paddingBottom:"3 rem"}}>
          <Line
            data={data}
            options={{
              responsive: true,
              plugins: { legend: { position: "top" } }
            }}
          />
        </div>
      </div>
      <Modal points={points} />
    </div>
  )
}

export default ViewGraphs
