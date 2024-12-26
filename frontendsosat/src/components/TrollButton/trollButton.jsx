import { useNavigate } from 'react-router-dom';
import styles from './trollButton.module.css';
import axios from 'axios';
import { useEffect } from 'react';
const TrollButton=({to})=>{
  const navigate=useNavigate();
  useEffect(()=>{
  const startup=async ()=>{
    const response=await axios.get("http://localhost:8080/api/tabulatedFunctions/getFunctionNames",{})
    const response2=await axios.post("http://localhost:8080/api/tabulatedFunctions/getFunctions",{})
    sessionStorage.setItem("functions",(response2.data))
    sessionStorage.setItem("functionNames",(response.data))
  };
startup();},[]);
  
  return(
    
    <button className={styles.trollButton} onClick={()=>{
      navigate(`/${to}`)
    }}>
        XDDDDDD!!!!
    </button>
  );
};
export default TrollButton;