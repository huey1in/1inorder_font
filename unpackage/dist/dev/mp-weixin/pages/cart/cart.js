"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const common_assets = require("../../common/assets.js");
class CartItem extends UTS.UTSType {
  static get$UTSMetadata$() {
    return {
      kind: 2,
      get fields() {
        return {
          id: { type: Number, optional: false },
          product_id: { type: Number, optional: false },
          name: { type: String, optional: false },
          price: { type: Number, optional: false },
          quantity: { type: Number, optional: false },
          images: { type: "Unknown", optional: false },
          selected: { type: Boolean, optional: false }
        };
      },
      name: "CartItem"
    };
  }
  constructor(options, metadata = CartItem.get$UTSMetadata$(), isJSONParse = false) {
    super();
    this.__props__ = UTS.UTSType.initProps(options, metadata, isJSONParse);
    this.id = this.__props__.id;
    this.product_id = this.__props__.product_id;
    this.name = this.__props__.name;
    this.price = this.__props__.price;
    this.quantity = this.__props__.quantity;
    this.images = this.__props__.images;
    this.selected = this.__props__.selected;
    delete this.__props__;
  }
}
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      cartItems: []
    };
  },
  computed: {
    allSelected() {
      return this.cartItems.length > 0 && this.cartItems.every((item) => {
        return item.selected;
      });
    },
    selectedCount() {
      return this.cartItems.filter((item) => {
        return item.selected;
      }).reduce((sum, item) => {
        return sum + item.quantity;
      }, 0);
    },
    totalAmount() {
      return this.cartItems.filter((item) => {
        return item.selected;
      }).reduce((sum, item) => {
        return sum + (item.price || 0) * item.quantity;
      }, 0);
    }
  },
  onShow() {
    common_vendor.index.__f__("log", "at pages/cart/cart.uvue:92", "购物车页面 onShow 触发");
    this.fetchCart();
  },
  onLoad() {
    common_vendor.index.__f__("log", "at pages/cart/cart.uvue:96", "购物车页面 onLoad 触发");
    this.fetchCart();
  },
  methods: {
    getProductImage(item) {
      if (item.images && item.images.length > 0) {
        return item.images[0];
      }
      return DEFAULT_FOOD_IMAGE;
    },
    fetchCart() {
      common_vendor.index.__f__("log", "at pages/cart/cart.uvue:108", "fetchCart 开始执行");
      const token = common_vendor.index.getStorageSync("token");
      common_vendor.index.__f__("log", "at pages/cart/cart.uvue:110", "token:", token ? "已获取" : "未获取");
      if (!token) {
        common_vendor.index.__f__("log", "at pages/cart/cart.uvue:112", "未登录，跳转到登录页");
        common_vendor.index.showToast({ title: "请先登录", icon: "none" });
        setTimeout(() => {
          common_vendor.index.navigateTo({ url: "/pages/login/login" });
        }, 1500);
        return null;
      }
      common_vendor.index.showLoading({ title: "加载中..." });
      common_vendor.index.__f__("log", "at pages/cart/cart.uvue:121", "开始请求购物车接口");
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart`,
        method: "GET",
        header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
        success: (res = null) => {
          common_vendor.index.__f__("log", "at pages/cart/cart.uvue:127", "购物车接口响应:", UTS.JSON.stringify(res.data));
          if (res.data.success && res.data.data.cart) {
            const items = res.data.data.cart.items || [];
            this.cartItems = items.map((item = null) => {
              return new UTSJSONObject({
                id: item.cart_item_id,
                product_id: item.product_id,
                name: item.name,
                price: item.price,
                quantity: item.quantity,
                images: item.images || [],
                selected: true
              });
            });
            common_vendor.index.__f__("log", "at pages/cart/cart.uvue:140", "处理后的购物车数据:", UTS.JSON.stringify(this.cartItems));
          }
        },
        fail: () => {
          common_vendor.index.showToast({ title: "加载失败", icon: "none" });
        },
        complete: () => {
          common_vendor.index.hideLoading();
        }
      });
    },
    toggleSelect(item) {
      item.selected = !item.selected;
    },
    toggleSelectAll() {
      const newStatus = !this.allSelected;
      this.cartItems.forEach((item) => {
        item.selected = newStatus;
      });
    },
    changeQuantity(item, delta) {
      const newQty = item.quantity + delta;
      if (newQty < 1)
        return null;
      const token = common_vendor.index.getStorageSync("token");
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart/item/${item.id}`,
        method: "PATCH",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }),
        data: new UTSJSONObject({
          quantity: newQty
        }),
        success: (res = null) => {
          if (res.data.success) {
            item.quantity = newQty;
          }
        }
      });
    },
    removeItem(item) {
      common_vendor.index.showModal(new UTSJSONObject({
        title: "确认删除",
        content: "确定要删除该商品吗？",
        success: (res) => {
          if (res.confirm) {
            const token = common_vendor.index.getStorageSync("token");
            common_vendor.index.request({
              url: `${common_config.BASE_URL}/cart/item/${item.id}`,
              method: "DELETE",
              header: new UTSJSONObject({
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
              }),
              success: (res2 = null) => {
                if (res2.data.success) {
                  const index = this.cartItems.indexOf(item);
                  if (index > -1) {
                    this.cartItems.splice(index, 1);
                  }
                }
              }
            });
          }
        }
      }));
    },
    checkout() {
      const selectedItems = this.cartItems.filter((item) => {
        return item.selected;
      });
      if (selectedItems.length === 0) {
        common_vendor.index.showToast({ title: "请选择商品", icon: "none" });
        return null;
      }
      common_vendor.index.navigateTo({ url: "/pages/order/create" });
    },
    goShopping() {
      common_vendor.index.navigateBack();
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: $data.cartItems.length === 0
  }, $data.cartItems.length === 0 ? {
    b: common_assets._imports_1,
    c: common_vendor.o((...args) => $options.goShopping && $options.goShopping(...args))
  } : {}, {
    d: $data.cartItems.length > 0
  }, $data.cartItems.length > 0 ? {
    e: common_vendor.f($data.cartItems, (item, k0, i0) => {
      return {
        a: common_vendor.t(item.selected ? "♡" : ""),
        b: common_vendor.n(item.selected ? "checked" : ""),
        c: common_vendor.o(($event) => $options.toggleSelect(item), item.id),
        d: $options.getProductImage(item),
        e: common_vendor.t(item.name || "商品"),
        f: common_vendor.t(item.price || 0),
        g: common_vendor.o(($event) => $options.changeQuantity(item, -1), item.id),
        h: common_vendor.t(item.quantity),
        i: common_vendor.o(($event) => $options.changeQuantity(item, 1), item.id),
        j: common_vendor.o(($event) => $options.removeItem(item), item.id),
        k: item.id
      };
    }),
    f: common_assets._imports_1$1
  } : {}, {
    g: $data.cartItems.length > 0
  }, $data.cartItems.length > 0 ? common_vendor.e({
    h: $options.allSelected
  }, $options.allSelected ? {} : {}, {
    i: common_vendor.n($options.allSelected ? "checked" : ""),
    j: common_vendor.o((...args) => $options.toggleSelectAll && $options.toggleSelectAll(...args)),
    k: common_vendor.t($options.totalAmount.toFixed(2)),
    l: common_vendor.t($options.selectedCount),
    m: common_vendor.o((...args) => $options.checkout && $options.checkout(...args))
  }) : {}, {
    n: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/cart/cart.js.map
