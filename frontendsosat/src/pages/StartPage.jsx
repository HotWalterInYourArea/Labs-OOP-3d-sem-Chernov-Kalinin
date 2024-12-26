import { useState } from 'react'
import perelman from '../assets/sticker.webp'
import TrollButton from '../components/TrollButton/trollButton'
import {Link} from 'react-router-dom'
function StartPage() {
  const [count, setCount] = useState(0)

  return (
    <>
    <div>
      <img src={perelman} style={{width: 200, height: 200, position: 'relative', marginBottom: '100px'}}/>
    </div>
    <h2 style={{marginBottom:'30px'}}>КАЛ МАТ ФУНК</h2>
    <div className="cardDiv">
      <button className="card" onClick={()=>setCount((count+1))}>COUNT IS {count}</button>
    </div>
    <TrollButton to="app"/>
    </>
  )
}

export default StartPage;