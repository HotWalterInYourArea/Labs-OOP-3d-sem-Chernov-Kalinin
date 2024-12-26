import { BrowserRouter as Router, Routes } from "react-router-dom"
import StartPage from "./pages/StartPage"
import ActualApp from "./pages/ActualApp"
const AppRoutes=()=>{
    return (
        <Router>
            <StartPage/>
            <Routes>
                <Route path="/" element={<StartPage/>}/>
                <Route path="/app" element={<ActualApp/>}/>
            </Routes>
        </Router>

    )
}
export default AppRoutes;