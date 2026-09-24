import { NavLink } from "react-router-dom";
import {
  LayoutDashboard,
  Users,
  Building2,
  GitMerge,
  ListOrdered,
  SearchCode,
  Network,
  Info,
  Target,
} from "lucide-react";

const navItems = [
  { to: "/dashboard", label: "Dashboard", icon: LayoutDashboard },
  { to: "/students", label: "Students", icon: Users },
  { to: "/companies", label: "Companies", icon: Building2 },
  { to: "/matching", label: "Placement Matching", icon: GitMerge },
  { to: "/ranking", label: "Candidate Ranking", icon: ListOrdered },
  { to: "/pattern-search", label: "Pattern Search", icon: SearchCode },
  { to: "/dsa-algorithms", label: "DSA Algorithms", icon: Network },
  { to: "/about", label: "About Project", icon: Info },
];

export default function Sidebar({ onNavigate }) {
  return (
    <div className="flex h-full flex-col bg-navy-900">
      <div className="flex items-center gap-2.5 px-6 py-6">
        <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-accent-500">
          <Target className="h-5 w-5 text-white" strokeWidth={2.25} />
        </div>
        <div>
          <p className="font-display text-[15px] font-bold leading-none text-white">
            Placement Hub
          </p>
          <p className="mt-1 text-[11px] leading-none text-navy-300">
            Smart Matching System
          </p>
        </div>
      </div>

      <nav className="mt-2 flex-1 space-y-0.5 overflow-y-auto px-3">
        {navItems.map(({ to, label, icon: Icon }) => (
          <NavLink
            key={to}
            to={to}
            onClick={onNavigate}
            className={({ isActive }) =>
              `focus-ring group flex items-center gap-3 rounded-lg px-3 py-2.5 text-[13.5px] font-medium transition-colors ${
                isActive
                  ? "bg-accent-500/15 text-white"
                  : "text-navy-300 hover:bg-white/5 hover:text-white"
              }`
            }
          >
            {({ isActive }) => (
              <>
                <Icon
                  className={`h-[18px] w-[18px] shrink-0 transition-colors ${
                    isActive ? "text-accent-400" : "text-navy-400 group-hover:text-navy-200"
                  }`}
                  strokeWidth={2}
                />
                <span>{label}</span>
                {isActive && (
                  <span className="ml-auto h-1.5 w-1.5 rounded-full bg-accent-400" />
                )}
              </>
            )}
          </NavLink>
        ))}
      </nav>

      <div className="mx-3 mb-5 mt-2 rounded-xl border border-white/10 bg-white/5 px-4 py-3.5">
        <p className="text-[12.5px] font-semibold text-white">Team Project</p>
        <p className="mt-0.5 text-[11.5px] text-navy-300">DSA • Java • MySQL</p>
      </div>
    </div>
  );
}
