"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const common_assets = require("../../common/assets.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      productId: 0,
      defaultFoodImage: DEFAULT_FOOD_IMAGE,
      product: new UTSJSONObject({
        id: 0,
        name: "",
        description: "",
        detail: "",
        price: 0,
        original_price: 0,
        images: [],
        sales_count: 0,
        stock_quantity: 0,
        category_name: ""
      }),
      cartCount: 0
    };
  },
  onLoad(options = null) {
    if (options.id) {
      this.productId = parseInt(options.id);
      this.fetchProduct();
      this.fetchCartCount();
    }
  },
  methods: {
    fetchProduct() {
      common_vendor.index.showLoading({ title: "加载中..." });
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/products/${this.productId}`,
        method: "GET",
        success: (res = null) => {
          if (res.data.success) {
            const data = res.data.data;
            if (data.product) {
              this.product = data.product;
            } else if (data.products && data.products.length > 0) {
              this.product = data.products[0];
            } else {
              common_vendor.index.showToast({ title: "商品不存在", icon: "none" });
            }
          } else {
            common_vendor.index.showToast({ title: "商品不存在", icon: "none" });
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
    fetchCartCount() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token)
        return null;
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart/count`,
        method: "GET",
        header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
        success: (res = null) => {
          if (res.data.success) {
            this.cartCount = res.data.data.count || 0;
          }
        }
      });
    },
    addToCart() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        common_vendor.index.showToast({ title: "请先登录", icon: "none" });
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return null;
      }
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart/add`,
        method: "POST",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }),
        data: new UTSJSONObject({
          product_id: this.productId,
          quantity: 1
        }),
        success: (res = null) => {
          if (res.data.success) {
            common_vendor.index.showToast({ title: "已加入购物车", icon: "success" });
            this.fetchCartCount();
          } else {
            common_vendor.index.showToast({ title: res.data.message || "添加失败", icon: "none" });
          }
        }
      });
    },
    buyNow() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        common_vendor.index.showToast({ title: "请先登录", icon: "none" });
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return null;
      }
      common_vendor.index.navigateTo({
        url: `/pages/order/create?product_id=${this.productId}&quantity=1`
      });
    },
    goToHome() {
      common_vendor.index.switchTab({ url: "/pages/index/index" });
    },
    goToCart() {
      common_vendor.index.switchTab({ url: "/pages/cart/cart" });
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: common_vendor.f($data.product.images, (img, index, i0) => {
      return {
        a: img,
        b: index
      };
    }),
    b: !$data.product.images || $data.product.images.length === 0
  }, !$data.product.images || $data.product.images.length === 0 ? {
    c: $data.defaultFoodImage
  } : {}, {
    d: common_vendor.t($data.product.name),
    e: common_vendor.t($data.product.price),
    f: $data.product.original_price
  }, $data.product.original_price ? {
    g: common_vendor.t($data.product.original_price)
  } : {}, {
    h: common_vendor.t($data.product.sales_count || 0),
    i: $data.product.category_name
  }, $data.product.category_name ? {
    j: common_vendor.t($data.product.category_name)
  } : {}, {
    k: common_vendor.t($data.product.stock_quantity || 0),
    l: common_vendor.t($data.product.detail || $data.product.description || "暂无详细介绍"),
    m: common_assets._imports_0$2,
    n: common_vendor.o((...args) => $options.goToHome && $options.goToHome(...args)),
    o: common_assets._imports_1,
    p: $data.cartCount > 0
  }, $data.cartCount > 0 ? {
    q: common_vendor.t($data.cartCount)
  } : {}, {
    r: common_vendor.o((...args) => $options.goToCart && $options.goToCart(...args)),
    s: common_vendor.o((...args) => $options.addToCart && $options.addToCart(...args)),
    t: common_vendor.o((...args) => $options.buyNow && $options.buyNow(...args)),
    v: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/product/detail.js.map
