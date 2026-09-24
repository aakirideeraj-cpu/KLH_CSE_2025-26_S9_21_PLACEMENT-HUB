/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        display: ["Sora", "system-ui", "sans-serif"],
        sans: ["Inter", "system-ui", "sans-serif"],
        mono: ["JetBrains Mono", "ui-monospace", "monospace"],
      },
      colors: {
        navy: {
          50: "#f1f4fb",
          100: "#dfe5f4",
          200: "#b9c6e6",
          300: "#8ea1d3",
          400: "#5f74b8",
          500: "#3f529d",
          600: "#2d3c7e",
          700: "#212d63",
          800: "#141b40",
          900: "#0b1130",
          950: "#070a1f",
        },
        accent: {
          50: "#eef3ff",
          100: "#dbe6ff",
          200: "#b8cdff",
          300: "#8aabff",
          400: "#5b85fb",
          500: "#3763f0",
          600: "#2547d6",
          700: "#1e39ab",
          800: "#1c3388",
          900: "#1c306e",
        },
      },
      boxShadow: {
        card: "0 1px 2px rgba(11,17,48,0.04), 0 8px 24px -12px rgba(11,17,48,0.12)",
        cardHover: "0 4px 12px rgba(11,17,48,0.06), 0 16px 32px -12px rgba(11,17,48,0.18)",
      },
      keyframes: {
        fadeUp: {
          "0%": { opacity: 0, transform: "translateY(8px)" },
          "100%": { opacity: 1, transform: "translateY(0)" },
        },
      },
      animation: {
        fadeUp: "fadeUp 0.5s ease-out both",
      },
    },
  },
  plugins: [],
}
