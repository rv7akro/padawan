import { vitePreprocess } from "@sveltejs/kit/vite";
import adapter from "@sveltejs/adapter-static";

/** @type {import('@sveltejs/kit').Config} */
const config = {
  onwarn: (warning, handler) => {
    if (warning.code.includes("a11y")) {
      return;
    }
    handler(warning)
  },
  kit: {
    adapter: adapter({
      fallback: '200.html'
    }),
    prerender: {
      handleMissingId: "warn",
    },
  },
  preprocess: [vitePreprocess({})],
};

export default config;
