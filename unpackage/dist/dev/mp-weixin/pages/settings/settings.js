"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      cacheSize: "0KB"
    };
  },
  onLoad() {
    this.getCacheSize();
  },
  methods: {
    getCacheSize() {
      try {
        const info = common_vendor.index.getStorageInfoSync();
        if (info && info.currentSize) {
          const size = info.currentSize;
          if (size < 1024) {
            this.cacheSize = size + "KB";
          } else {
            this.cacheSize = (size / 1024).toFixed(2) + "MB";
          }
        }
      } catch (e) {
        this.cacheSize = "0KB";
      }
    },
    goChangePassword() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        common_vendor.index.showToast({ title: "请先登录", icon: "none" });
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return null;
      }
      common_vendor.index.navigateTo({ url: "/pages/settings/password" });
    },
    goEditProfile() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        common_vendor.index.showToast({ title: "请先登录", icon: "none" });
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return null;
      }
      common_vendor.index.navigateTo({ url: "/pages/settings/profile" });
    },
    goAgreement() {
      common_vendor.index.navigateTo({ url: "/pages/legal/agreement" });
    },
    goPrivacy() {
      common_vendor.index.navigateTo({ url: "/pages/legal/privacy" });
    },
    clearCache() {
      common_vendor.index.showModal(new UTSJSONObject({
        title: "提示",
        content: "确定要清除缓存吗？",
        success: (res) => {
          if (res.confirm) {
            const token = common_vendor.index.getStorageSync("token");
            const userInfo = common_vendor.index.getStorageSync("userInfo");
            common_vendor.index.clearStorageSync();
            if (token)
              common_vendor.index.setStorageSync("token", token);
            if (userInfo)
              common_vendor.index.setStorageSync("userInfo", userInfo);
            this.cacheSize = "0KB";
            common_vendor.index.showToast({ title: "缓存已清除", icon: "success" });
          }
        }
      }));
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_assets._imports_0$5,
    b: common_vendor.o((...args) => $options.goChangePassword && $options.goChangePassword(...args)),
    c: common_assets._imports_1$5,
    d: common_vendor.o((...args) => $options.goEditProfile && $options.goEditProfile(...args)),
    e: common_assets._imports_2$1,
    f: common_vendor.o((...args) => $options.goAgreement && $options.goAgreement(...args)),
    g: common_assets._imports_3,
    h: common_vendor.o((...args) => $options.goPrivacy && $options.goPrivacy(...args)),
    i: common_assets._imports_4,
    j: common_assets._imports_5,
    k: common_vendor.t($data.cacheSize),
    l: common_vendor.o((...args) => $options.clearCache && $options.clearCache(...args)),
    m: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/settings/settings.js.map
