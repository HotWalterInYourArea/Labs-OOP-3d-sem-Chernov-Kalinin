import { useState } from 'react'
import reactLogo from './assets/react.svg'
import perelman from './assets/sticker.webp'
import viteLogo from '/vite.svg'
import { BrowserRouter as Router,Routes,Route } from 'react-router-dom'
import './App.css'
import StartPage from './pages/StartPage'
import ActualApp from './pages/ActualApp'
import { createSession } from 'react-router-dom'
function App() {
  const [count, setCount] = useState(0)

  return (
    <>
    <Router>
      <Routes>
        <Route path="/" element={<StartPage/>}/>
        <Route path="app/*" element={<ActualApp/>}/>
      </Routes>
    </Router>
    </>
  )
}

export default App;
