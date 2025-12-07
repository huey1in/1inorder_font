"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      isLoggedIn: false,
      defaultAvatarImage: "/static/icons/default-avatar.png",
      userInfo: new UTSJSONObject({
        username: "",
        nickname: "",
        phone: "",
        avatar: ""
      })
    };
  },
  computed: {
    displayName() {
      const info = this.userInfo || {};
      if (info.nickname)
        return info.nickname;
      return "用户";
    }
  },
  onShow() {
    this.checkLoginStatus();
  },
  methods: {
    checkLoginStatus() {
      const token = common_vendor.index.getStorageSync("token");
      const userInfo = common_vendor.index.getStorageSync("userInfo");
      this.isLoggedIn = !!token;
      if (userInfo) {
        this.userInfo = userInfo;
      }
    },
    goLogin() {
      common_vendor.index.navigateTo({ url: "/pages/login/login" });
    },
    goOrderList(status = null) {
      if (!this.isLoggedIn) {
        common_vendor.index.showToast({ title: "请先登录", icon: "none" });
        this.goLogin();
        return null;
      }
      if (status) {
        common_vendor.index.setStorageSync("orderStatus", status);
      }
      common_vendor.index.switchTab({ url: "/pages/order/list" });
    },
    goAddress() {
      if (!this.isLoggedIn) {
        common_vendor.index.showToast({ title: "请先登录", icon: "none" });
        this.goLogin();
        return null;
      }
      common_vendor.index.navigateTo({ url: "/pages/address/list" });
    },
    goSettings() {
      common_vendor.index.navigateTo({ url: "/pages/settings/settings" });
    },
    handleLogout() {
      common_vendor.index.showModal(new UTSJSONObject({
        title: "提示",
        content: "确定要退出登录吗？",
        success: (res) => {
          if (res.confirm) {
            common_vendor.index.removeStorageSync("token");
            common_vendor.index.removeStorageSync("userInfo");
            this.isLoggedIn = false;
            this.userInfo = { username: "", phone: "" };
            common_vendor.index.showToast({ title: "已退出登录", icon: "success" });
          }
        }
      }));
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: $data.userInfo.avatar || $data.defaultAvatarImage,
    b: $data.isLoggedIn
  }, $data.isLoggedIn ? common_vendor.e({
    c: common_vendor.t($options.displayName),
    d: $data.userInfo.phone
  }, $data.userInfo.phone ? {
    e: common_vendor.t($data.userInfo.phone)
  } : {}) : {
    f: common_vendor.o((...args) => $options.goLogin && $options.goLogin(...args))
  }, {
    g: common_vendor.n($data.isLoggedIn ? "logged-in" : ""),
    h: common_assets._imports_0$1,
    i: common_vendor.o((...args) => $options.goAddress && $options.goAddress(...args)),
    j: common_assets._imports_1$2,
    k: common_vendor.o((...args) => $options.goSettings && $options.goSettings(...args)),
    l: $data.isLoggedIn
  }, $data.isLoggedIn ? {
    m: common_vendor.o((...args) => $options.handleLogout && $options.handleLogout(...args))
  } : {}, {
    n: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/my/my.js.map
