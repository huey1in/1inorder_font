"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const common_assets = require("../../common/assets.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      phone: "",
      password: "",
      confirmPassword: ""
    };
  },
  methods: {
    handleRegister() {
      var _a;
      return common_vendor.__awaiter(this, void 0, void 0, function* () {
        if (!this.phone) {
          common_vendor.index.showToast({ title: "请输入手机号", icon: "none" });
          return Promise.resolve(null);
        }
        if (!this.password) {
          common_vendor.index.showToast({ title: "请输入密码", icon: "none" });
          return Promise.resolve(null);
        }
        if (this.password.length < 6 || this.password.length > 20) {
          common_vendor.index.showToast({ title: "密码长度为6-20位", icon: "none" });
          return Promise.resolve(null);
        }
        if (this.password !== this.confirmPassword) {
          common_vendor.index.showToast({ title: "两次输入密码不一致", icon: "none" });
          return Promise.resolve(null);
        }
        let loadingShown = false;
        try {
          common_vendor.index.showLoading({ title: "注册中..." });
          loadingShown = true;
          const res = yield new Promise((resolve, reject) => {
            common_vendor.index.__f__("log", "at pages/register/register.uvue:88", "注册请求 payload:", new UTSJSONObject({
              phone: this.phone,
              password: this.password
            }));
            common_vendor.index.request({
              url: `${common_config.BASE_URL}/auth/register`,
              method: "POST",
              header: new UTSJSONObject({
                "Content-Type": "application/json"
              }),
              data: new UTSJSONObject({
                phone: this.phone,
                password: this.password
              }),
              success: (resp) => {
                common_vendor.index.__f__("log", "at pages/register/register.uvue:103", "注册响应:", resp);
                resolve(resp);
              },
              fail: reject
            });
          });
          if ((res.statusCode === 200 || res.statusCode === 201) && res.data && res.data.success) {
            if (res.data.data && res.data.data.token) {
              common_vendor.index.setStorageSync("token", res.data.data.token);
              if (res.data.data.user) {
                common_vendor.index.setStorageSync("userInfo", res.data.data.user);
              }
            }
            common_vendor.index.showToast({ title: "注册成功", icon: "success" });
            setTimeout(() => {
              common_vendor.index.reLaunch({ url: "/pages/index/index" });
            }, 1200);
          } else {
            const msg = ((_a = res.data) === null || _a === void 0 ? null : _a.message) || `注册失败（${res.statusCode || "未知错误"}）`;
            common_vendor.index.showToast({ title: msg, icon: "none" });
          }
        } catch (error) {
          common_vendor.index.showToast({ title: "网络错误", icon: "none" });
        } finally {
          if (loadingShown) {
            common_vendor.index.hideLoading();
          }
        }
      });
    },
    goToLogin() {
      const pages = getCurrentPages();
      if (pages.length > 1) {
        common_vendor.index.navigateBack();
      } else {
        common_vendor.index.redirectTo({ url: "/pages/login/login" });
      }
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
    g: common_assets._imports_1$3,
    h: $data.confirmPassword,
    i: common_vendor.o(($event) => $data.confirmPassword = $event.detail.value),
    j: common_vendor.o((...args) => $options.handleRegister && $options.handleRegister(...args)),
    k: common_vendor.o((...args) => $options.goToLogin && $options.goToLogin(...args)),
    l: common_vendor.o((...args) => $options.goAgreement && $options.goAgreement(...args)),
    m: common_vendor.o((...args) => $options.goPrivacy && $options.goPrivacy(...args)),
    n: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/register/register.js.map
