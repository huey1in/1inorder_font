"use strict";
Object.defineProperty(exports, Symbol.toStringTag, { value: "Module" });
const common_vendor = require("./common/vendor.js");
if (!Math) {
  "./pages/index/index.js";
  "./pages/cart/cart.js";
  "./pages/my/my.js";
  "./pages/login/login.js";
  "./pages/register/register.js";
  "./pages/product/detail.js";
  "./pages/order/create.js";
  "./pages/order/list.js";
  "./pages/order/detail.js";
  "./pages/address/list.js";
  "./pages/address/edit.js";
  "./pages/legal/agreement.js";
  "./pages/legal/privacy.js";
  "./pages/settings/settings.js";
  "./pages/settings/password.js";
  "./pages/settings/profile.js";
}
const _sfc_main = common_vendor.defineComponent(new UTSJSONObject({
  onLaunch: function() {
    common_vendor.index.__f__("log", "at App.uvue:7", "App Launch");
  },
  onShow: function() {
    common_vendor.index.__f__("log", "at App.uvue:10", "App Show");
  },
  onHide: function() {
    common_vendor.index.__f__("log", "at App.uvue:13", "App Hide");
  },
  onExit: function() {
    common_vendor.index.__f__("log", "at App.uvue:34", "App Exit");
  }
}));
function createApp() {
  const app = common_vendor.createSSRApp(_sfc_main);
  return {
    app
  };
}
createApp().app.mount("#app");
exports.createApp = createApp;
//# sourceMappingURL=../.sourcemap/mp-weixin/app.js.map
