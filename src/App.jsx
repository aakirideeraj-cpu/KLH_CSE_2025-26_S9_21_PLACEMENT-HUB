import { Routes, Route, Navigate } from "react-router-dom";
import ToastProvider from "./components/ToastProvider";
import AppLayout from "./layouts/AppLayout";
import Landing from "./pages/Landing";
import Dashboard from "./pages/Dashboard";
import Students from "./pages/Students";
import Companies from "./pages/Companies";
import PlacementMatching from "./pages/PlacementMatching";
import CandidateRanking from "./pages/CandidateRanking";
import PatternSearch from "./pages/PatternSearch";
import DsaAlgorithms from "./pages/DsaAlgorithms";
import AboutProject from "./pages/AboutProject";

export default function App() {
  return (
    <ToastProvider>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route element={<AppLayout />}>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/students" element={<Students />} />
          <Route path="/companies" element={<Companies />} />
          <Route path="/matching" element={<PlacementMatching />} />
          <Route path="/ranking" element={<CandidateRanking />} />
          <Route path="/pattern-search" element={<PatternSearch />} />
          <Route path="/dsa-algorithms" element={<DsaAlgorithms />} />
          <Route path="/about" element={<AboutProject />} />
        </Route>
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </ToastProvider>
  );
}
