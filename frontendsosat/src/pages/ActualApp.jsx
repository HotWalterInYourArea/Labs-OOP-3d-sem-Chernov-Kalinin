import {
    Routes,
    Route,
    Outlet,
    Link,
    useMatch,
    useResolvedPath,
  } from "react-router-dom";
  import React, { useState, useEffect } from 'react';
  import"./ActualApp.css"
  import { Settings } from "lucide-react";
  import Cookies from 'js-cookie';
  import Layout from "../components/Layout/Layout";
import CreateTableFunction from "./createTableFunction";
import DifferentiationIntegration from "./DifferIntegration";
import ArithmeticOperations from "./ArithmeticOperations";
import CreateComplexFunction from "./CreateComplexFunction";
import ViewGraphs from "./CreateGraph";
  export default function ActualApp() {
    return (
      <div>  
        <Routes>
          <Route path="/" element={<Layout/>}>
            <Route index element={<Home/>} />
            <Route path="createTableFunction" element={<CreateTableFunction />} />
            <Route path="differIntegration" element={<DifferentiationIntegration />} />
            <Route path="arithmeticOperations" element={<ArithmeticOperations />} />
            <Route path="createComplexFunction" element={<CreateComplexFunction />} />
            <Route path="createGraph" element={<ViewGraphs />} />
          </Route>
        </Routes>
      </div>
    );
  }
  
  
  function Home() {
    return (
      <div style={{justifySelf:"center"}}>
        <h2>ТЫ ЧТО ТУТ ДЕЛАЕШЬ?</h2>
      </div>
    );
  }