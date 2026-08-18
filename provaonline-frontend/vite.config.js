import { defineConfig } from "vite";
import { svelte } from "@sveltejs/vite-plugin-svelte";

export default defineConfig({
  plugins: [svelte()],
  build: {
    emptyOutDir: true,
    outDir: "../provaonline-backend/src/main/resources/static",
  },
  server: {
    proxy: {
      "/api": "http://localhost:8080",
    },
  },
});