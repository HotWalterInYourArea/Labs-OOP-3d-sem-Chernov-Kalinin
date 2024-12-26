import { Settings } from "lucide-react";
import Cookies from 'js-cookie';
import {
    Routes,
    Route,
    Outlet,
    Link,
    useMatch,
    useResolvedPath,
  } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import CustomLink from "../CustomLinks/CustomLink";
import styles from "./Layout.module.css"
export default  function Layout() {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedFactory, setSelectedFactory] = useState(null);
    useEffect(() => {
        const factoryType = Cookies.get('fabricType');
        setSelectedFactory(factoryType || 'ARRAY');
    }, []);
    const handleSettingsClick = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleFactoryChange = (factoryType) => {
        Cookies.set('fabricType', factoryType);
        setSelectedFactory(factoryType);
        setIsModalOpen(false);
    };
    return (
      <div>
        <nav>
          <ul>
            <li>
              <CustomLink to="/app/createTableFunction">Создать функцию</CustomLink>
            </li>
            <li>
              <CustomLink to="/app/differIntegration">Дифференцирование и Интегрирование</CustomLink>
            </li>
            <li>
              <CustomLink to="/app/arithmeticOperations">Арифметические операции</CustomLink>
            </li>
            <li>
              <CustomLink to="/app/createComplexFunction">Создание функций</CustomLink>
            </li>
            <li>
              <CustomLink to="/app/createGraph">Создание графов</CustomLink>
            </li>
          </ul>
        </nav>
        <hr />  
        <Outlet />
        <div className="settings">
        <button  onClick={handleSettingsClick}>
                <Settings size={28}/>
        </button>
        </div>
    {isModalOpen &&(
        <div className="modal">
            <div className="modal-content">
                <h2>Выбор фабрики</h2>
                <div>
                    <p>Текущая фабрика:<span>{selectedFactory}</span></p>
                 </div>
                 <div>
                            <button
                                onClick={() => handleFactoryChange('ARRAY')}

                            >
                                Array Factory
                            </button>
                            <button
                                onClick={() => handleFactoryChange('LINKEDLIST')}                            >
                                LinkedList Factory
                            </button>
                        </div>
                        <button
                            onClick={handleCloseModal}
                        >
                            Закрыть
                        </button>
            </div>
        </div>
    )};
    </div>
);
  };