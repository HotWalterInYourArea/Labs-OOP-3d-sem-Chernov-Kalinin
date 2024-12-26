import {
    Routes,
    Route,
    Outlet,
    Link,
    useMatch,
    useResolvedPath,
  } from "react-router-dom";
  import React, { useState, useEffect } from 'react';
export default function CustomLink({ children, to, ...props }) {
    let resolved = useResolvedPath(to);
    let match = useMatch({ path: resolved.pathname, end: true });
  
    return (
      <div>
        <Link
          style={{ textDecoration: match ? "underline" : "none", color:"black" }}
          to={to}
          {...props}
        >
          {children}
        </Link>
      </div>
    );
  }