import { useState } from "react";
import { Outlet } from "react-router-dom";
import { X } from "lucide-react";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";
import { useToast } from "../components/ToastProvider";

export default function AppLayout() {
  const [mobileOpen, setMobileOpen] = useState(false);
  const { showToast } = useToast();

  return (
    <div className="flex min-h-screen bg-slate-50">
      {/* Desktop sidebar */}
      <aside className="hidden w-64 shrink-0 lg:block">
        <div className="fixed inset-y-0 left-0 w-64">
          <Sidebar />
        </div>
      </aside>

      {/* Mobile drawer */}
      {mobileOpen && (
        <div className="fixed inset-0 z-40 lg:hidden">
          <div
            className="absolute inset-0 bg-navy-950/50 backdrop-blur-sm"
            onClick={() => setMobileOpen(false)}
          />
          <div className="absolute inset-y-0 left-0 w-72 animate-fadeUp">
            <div className="relative h-full">
              <button
                onClick={() => setMobileOpen(false)}
                className="focus-ring absolute right-3 top-4 z-10 flex h-8 w-8 items-center justify-center rounded-lg text-navy-300 hover:bg-white/10"
                aria-label="Close menu"
              >
                <X className="h-4 w-4" />
              </button>
              <Sidebar onNavigate={() => setMobileOpen(false)} />
            </div>
          </div>
        </div>
      )}

      <div className="flex min-h-screen flex-1 flex-col lg:pl-64">
        <Topbar
          onMenuClick={() => setMobileOpen(true)}
          onNotify={() =>
            showToast("3 new placement matches generated for eligible students.", "info")
          }
        />
        <main className="flex-1 px-4 py-6 sm:px-6 lg:px-8 lg:py-8">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
