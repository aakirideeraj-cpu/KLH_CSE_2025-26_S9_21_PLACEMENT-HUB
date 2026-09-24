import { useState } from "react";
import { useLocation } from "react-router-dom";
import { Menu, Search, Bell, ChevronRight } from "lucide-react";

const titles = {
  "/dashboard": "Dashboard",
  "/students": "Students",
  "/companies": "Companies",
  "/matching": "Placement Matching",
  "/ranking": "Candidate Ranking",
  "/pattern-search": "Pattern Search",
  "/dsa-algorithms": "DSA Algorithms",
  "/about": "About Project",
};

export default function Topbar({ onMenuClick, onNotify }) {
  const { pathname } = useLocation();
  const [query, setQuery] = useState("");
  const current = titles[pathname] || "Placement Hub";

  return (
    <header className="sticky top-0 z-30 flex h-16 items-center gap-3 border-b border-slate-200 bg-white/85 px-4 backdrop-blur sm:px-6">
      <button
        onClick={onMenuClick}
        className="focus-ring -ml-1 flex h-9 w-9 items-center justify-center rounded-lg text-navy-600 hover:bg-slate-100 lg:hidden"
        aria-label="Open menu"
      >
        <Menu className="h-5 w-5" />
      </button>

      <div className="hidden items-center gap-1.5 text-[13px] text-slate-400 sm:flex">
        <span>Placement Hub</span>
        <ChevronRight className="h-3.5 w-3.5" />
        <span className="font-medium text-navy-800">{current}</span>
      </div>

      <div className="ml-auto flex items-center gap-2 sm:gap-3">
        <div className="relative hidden w-56 md:block lg:w-72">
          <Search className="pointer-events-none absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" />
          <input
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            type="text"
            placeholder="Search students, companies..."
            className="focus-ring w-full rounded-lg border border-slate-200 bg-slate-50 py-2 pl-9 pr-3 text-[13px] text-navy-800 placeholder:text-slate-400 focus:bg-white"
          />
        </div>

        <button
          onClick={onNotify}
          className="focus-ring relative flex h-9 w-9 items-center justify-center rounded-lg text-navy-600 hover:bg-slate-100"
          aria-label="Notifications"
        >
          <Bell className="h-[18px] w-[18px]" />
          <span className="absolute right-2 top-2 h-1.5 w-1.5 rounded-full bg-accent-500" />
        </button>

        <div className="flex items-center gap-2.5 border-l border-slate-200 pl-3">
          <div className="flex h-8 w-8 items-center justify-center rounded-full bg-navy-800 text-[12px] font-semibold text-white">
            PT
          </div>
          <div className="hidden leading-tight sm:block">
            <p className="text-[13px] font-medium text-navy-800">Placement Team</p>
            <p className="text-[11.5px] text-slate-400">Admin</p>
          </div>
        </div>
      </div>
    </header>
  );
}
