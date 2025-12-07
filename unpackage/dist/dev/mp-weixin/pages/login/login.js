"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const common_assets = require("../../common/assets.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      phone: "",
      password: ""
    };
  },
  methods: {
    handleLogin() {
      if (!this.phone) {
        common_vendor.index.showToast({ title: "请输入手机号", icon: "none" });
        return null;
      }
      if (!this.password) {
        common_vendor.index.showToast({ title: "请输入密码", icon: "none" });
        return null;
      }
      common_vendor.index.showLoading({ title: "登录中..." });
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/auth/login`,
        method: "POST",
        header: new UTSJSONObject({
          "Content-Type": "application/json"
        }),
        data: new UTSJSONObject({
          phone: this.phone,
          password: this.password
        }),
        success: (res = null) => {
          if (res.data.success && res.data.data.token) {
            common_vendor.index.setStorageSync("token", res.data.data.token);
            common_vendor.index.setStorageSync("userInfo", res.data.data.user);
            common_vendor.index.showToast({ title: "登录成功", icon: "success" });
            setTimeout(() => {
              common_vendor.index.navigateBack();
            }, 1500);
          } else {
            common_vendor.index.showToast({ title: res.data.message || "登录失败", icon: "none" });
          }
        },
        fail: () => {
          common_vendor.index.showToast({ title: "网络错误", icon: "none" });
        },
        complete: () => {
          common_vendor.index.hideLoading();
        }
      });
    },
    goToRegister() {
      common_vendor.index.navigateTo({ url: "/pages/register/register" });
    },
    goAgreement() {
      common_vendor.index.navigateTo({ url: "/pages/legal/agreement" });
    },
    goPrivacy() {
      common_vendor.index.navigateTo({ url: "/pages/legal/privacy" });
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_assets._imports_0,
    b: $data.phone,
    c: common_vendor.o(($event) => $data.phone = $event.detail.value),
    d: common_assets._imports_1$3,
    e: $data.password,
    f: common_vendor.o(($event) => $data.password = $event.detail.value),
    g: common_vendor.o((...args) => $options.handleLogin && $options.handleLogin(...args)),
    h: common_vendor.o((...args) => $options.goToRegister && $options.goToRegister(...args)),
    i: common_vendor.o((...args) => $options.goAgreement && $options.goAgreement(...args)),
    j: common_vendor.o((...args) => $options.goPrivacy && $options.goPrivacy(...args)),
    k: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/login/login.js.map
