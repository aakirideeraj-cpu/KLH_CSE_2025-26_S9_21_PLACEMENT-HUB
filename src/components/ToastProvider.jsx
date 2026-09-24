import { createContext, useCallback, useContext, useState } from "react";
import { CheckCircle2, Info, X } from "lucide-react";

const ToastContext = createContext(null);

export function useToast() {
  const ctx = useContext(ToastContext);
  if (!ctx) throw new Error("useToast must be used within ToastProvider");
  return ctx;
}

export default function ToastProvider({ children }) {
  const [toasts, setToasts] = useState([]);

  const showToast = useCallback((message, type = "info") => {
    const id = Date.now() + Math.random();
    setToasts((t) => [...t, { id, message, type }]);
    setTimeout(() => {
      setToasts((t) => t.filter((toast) => toast.id !== id));
    }, 3200);
  }, []);

  const dismiss = (id) => setToasts((t) => t.filter((toast) => toast.id !== id));

  return (
    <ToastContext.Provider value={{ showToast }}>
      {children}
      <div className="pointer-events-none fixed bottom-5 right-5 z-[100] flex w-[calc(100%-2.5rem)] max-w-sm flex-col gap-2">
        {toasts.map((toast) => (
          <div
            key={toast.id}
            className="animate-fadeUp pointer-events-auto flex items-start gap-2.5 rounded-xl border border-slate-200 bg-white px-4 py-3 shadow-cardHover"
          >
            {toast.type === "success" ? (
              <CheckCircle2 className="mt-0.5 h-4 w-4 shrink-0 text-emerald-500" />
            ) : (
              <Info className="mt-0.5 h-4 w-4 shrink-0 text-accent-500" />
            )}
            <p className="flex-1 text-[13px] leading-snug text-navy-700">{toast.message}</p>
            <button
              onClick={() => dismiss(toast.id)}
              className="focus-ring rounded p-0.5 text-slate-400 hover:text-navy-600"
              aria-label="Dismiss"
            >
              <X className="h-3.5 w-3.5" />
            </button>
          </div>
        ))}
      </div>
    </ToastContext.Provider>
  );
}
