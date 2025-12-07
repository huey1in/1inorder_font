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
          specs: { type: "Any", optional: true },
          notes: { type: String, optional: true }
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
    this.specs = this.__props__.specs;
    this.notes = this.__props__.notes;
    delete this.__props__;
  }
}
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      cartItems: [],
      orderInfo: new UTSJSONObject({
        name: "",
        phone: "",
        address: "",
        remark: "",
        tableNumber: ""
      }),
      orderType: "delivery",
      typeOptions: [
        new UTSJSONObject({ value: "delivery", label: "外卖" }),
        new UTSJSONObject({ value: "pickup", label: "自取" })
      ],
      shopInfo: new UTSJSONObject({
        delivery_fee: 0,
        min_order_amount: 0
      })
    };
  },
  computed: {
    subtotal() {
      return this.cartItems.reduce((sum, item) => {
        return sum + (item.price || 0) * item.quantity;
      }, 0);
    },
    deliveryFee() {
      return this.orderType === "delivery" ? this.shopInfo.delivery_fee || 0 : 0;
    },
    totalAmount() {
      return this.subtotal + this.deliveryFee;
    }
  },
  onLoad() {
    this.fetchCart();
    this.fetchDefaultAddress();
    this.fetchShopInfo();
    this.fillContactFromStorage();
  },
  methods: {
    fetchShopInfo() {
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/shop/info`,
        method: "GET",
        success: (res = null) => {
          if (res.data.success && res.data.data) {
            this.shopInfo = res.data.data;
          }
        }
      });
    },
    fillContactFromStorage() {
      const user = common_vendor.index.getStorageSync("userInfo");
      if (user) {
        if (!this.orderInfo.name)
          this.orderInfo.name = user.nickname || user.username || "";
        if (!this.orderInfo.phone && user.phone)
          this.orderInfo.phone = user.phone;
      }
    },
    changeOrderType(type) {
      this.orderType = type;
    },
    getOrderTypeLabel(type) {
      const found = UTS.arrayFind(this.typeOptions, (item = null) => {
        return item.value === type;
      });
      return found ? found.label : "外卖";
    },
    selectAddress() {
      common_vendor.index.navigateTo({ url: "/pages/address/list?select=true" });
    },
    setAddress(address = null) {
      this.orderInfo.name = address.contact_name || address.name;
      this.orderInfo.phone = address.contact_phone || address.phone;
      this.orderInfo.address = address.address || address.detail || "";
    },
    fetchDefaultAddress() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token)
        return null;
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/addresses/default`,
        method: "GET",
        header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
        success: (res = null) => {
          if (res.data.success && res.data.data.address) {
            this.setAddress(res.data.data.address);
          }
        }
      });
    },
    getProductImage(item) {
      if (item.images && item.images.length > 0) {
        return item.images[0];
      }
      return DEFAULT_FOOD_IMAGE;
    },
    fetchCart() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return null;
      }
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart`,
        method: "GET",
        header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
        success: (res = null) => {
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
                specs: item.specs || item.options || void 0,
                notes: item.notes || ""
              });
            });
          }
        }
      });
    },
    submitOrder() {
      var _a, _b, _c, _d, _e, _f, _g, _h, _j, _k, _l;
      return common_vendor.__awaiter(this, void 0, void 0, function* () {
        if (this.orderType === "delivery") {
          if (!this.orderInfo.name || !this.orderInfo.phone || !this.orderInfo.address) {
            common_vendor.index.showToast({ title: "请先选择收货地址", icon: "none" });
            return Promise.resolve(null);
          }
          const minAmount = this.shopInfo.min_order_amount || 0;
          if (minAmount > 0 && this.subtotal < minAmount) {
            common_vendor.index.showToast({ title: `商品金额不满足起送金额 ¥${minAmount}`, icon: "none" });
            return Promise.resolve(null);
          }
        }
        if (this.orderType === "pickup") {
          if (!this.orderInfo.name || !this.orderInfo.phone) {
            common_vendor.index.showToast({ title: "请先选择地址或登录补全联系人", icon: "none" });
            return Promise.resolve(null);
          }
        }
        if (this.cartItems.length === 0) {
          common_vendor.index.showToast({ title: "购物车为空", icon: "none" });
          return Promise.resolve(null);
        }
        const token = common_vendor.index.getStorageSync("token");
        common_vendor.index.showLoading({ title: "提交中..." });
        try {
          const payload = new UTSJSONObject({
            order_type: this.orderType,
            notes: this.orderInfo.remark,
            items: this.cartItems.map((item) => {
              const mapped = new UTSJSONObject({
                product_id: item.product_id,
                quantity: item.quantity
              });
              if (item.specs)
                mapped.specs = item.specs;
              if (item.notes)
                mapped.notes = item.notes;
              return mapped;
            }),
            cart_item_ids: this.cartItems.map((item) => {
              return item.id;
            })
          });
          if (this.orderType === "delivery") {
            payload.delivery_address = this.orderInfo.address;
            payload.contact_name = this.orderInfo.name;
            payload.contact_phone = this.orderInfo.phone;
          } else if (this.orderType === "pickup") {
            payload.contact_name = this.orderInfo.name;
            payload.contact_phone = this.orderInfo.phone;
          }
          const createRes = yield new Promise((resolve, reject) => {
            common_vendor.index.request({
              url: `${common_config.BASE_URL}/orders`,
              method: "POST",
              header: new UTSJSONObject({
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
              }),
              data: payload,
              success: resolve,
              fail: reject
            });
          });
          if (!((_a = createRes.data) === null || _a === void 0 ? null : _a.success)) {
            common_vendor.index.showToast({ title: ((_b = createRes.data) === null || _b === void 0 ? null : _b.message) || "下单失败", icon: "none" });
            return Promise.resolve(null);
          }
          const orderId = ((_e = (_d = (_c = createRes.data) === null || _c === void 0 ? null : _c.data) === null || _d === void 0 ? null : _d.order) === null || _e === void 0 ? null : _e.id) || ((_g = (_f = createRes.data) === null || _f === void 0 ? null : _f.data) === null || _g === void 0 ? null : _g.id) || ((_j = (_h = createRes.data) === null || _h === void 0 ? null : _h.data) === null || _j === void 0 ? null : _j.order_id);
          if (!orderId) {
            common_vendor.index.showToast({ title: "下单成功，但未获取订单ID", icon: "none" });
            common_vendor.index.switchTab({ url: "/pages/order/list" });
            return Promise.resolve(null);
          }
          const payRes = yield new Promise((resolve, reject) => {
            common_vendor.index.request({
              url: `${common_config.BASE_URL}/orders/${orderId}/pay`,
              method: "POST",
              header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
              success: resolve,
              fail: reject
            });
          });
          if ((_k = payRes.data) === null || _k === void 0 ? null : _k.success) {
            common_vendor.index.showToast({ title: "支付成功", icon: "success" });
            setTimeout(() => {
              common_vendor.index.redirectTo({ url: `/pages/order/detail?id=${orderId}` });
            }, 800);
          } else {
            common_vendor.index.showToast({ title: ((_l = payRes.data) === null || _l === void 0 ? null : _l.message) || "支付失败", icon: "none" });
          }
        } catch (e) {
          common_vendor.index.showToast({ title: "网络错误", icon: "none" });
        } finally {
          common_vendor.index.hideLoading();
        }
      });
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: common_vendor.f($data.typeOptions, (item, k0, i0) => {
      return {
        a: common_vendor.t(item.label),
        b: item.value,
        c: $data.orderType === item.value ? 1 : "",
        d: common_vendor.o(($event) => $options.changeOrderType(item.value), item.value)
      };
    }),
    b: $data.orderType === "delivery"
  }, $data.orderType === "delivery" ? common_vendor.e({
    c: $data.orderInfo.address
  }, $data.orderInfo.address ? {
    d: common_vendor.t($data.orderInfo.name),
    e: common_vendor.t($data.orderInfo.phone),
    f: common_vendor.t($data.orderInfo.address)
  } : {
    g: common_assets._imports_0$1
  }) : common_vendor.e({
    h: $data.orderInfo.name || $data.orderInfo.phone
  }, $data.orderInfo.name || $data.orderInfo.phone ? {
    i: common_vendor.t($data.orderInfo.name),
    j: common_vendor.t($data.orderInfo.phone)
  } : {
    k: common_assets._imports_0$1
  }), {
    l: common_vendor.o((...args) => $options.selectAddress && $options.selectAddress(...args)),
    m: common_vendor.f($data.cartItems, (item, k0, i0) => {
      return {
        a: $options.getProductImage(item),
        b: common_vendor.t(item.name || "商品"),
        c: common_vendor.t(item.price || 0),
        d: common_vendor.t(item.quantity),
        e: item.id
      };
    }),
    n: $data.orderInfo.remark,
    o: common_vendor.o(($event) => $data.orderInfo.remark = $event.detail.value),
    p: common_vendor.t($options.subtotal.toFixed(2)),
    q: $data.orderType === "delivery"
  }, $data.orderType === "delivery" ? {
    r: common_vendor.t($options.deliveryFee.toFixed(2))
  } : {}, {
    s: $data.orderType === "delivery" && $data.shopInfo.min_order_amount > 0
  }, $data.orderType === "delivery" && $data.shopInfo.min_order_amount > 0 ? {
    t: common_vendor.t($data.shopInfo.min_order_amount),
    v: common_vendor.t($options.subtotal >= $data.shopInfo.min_order_amount ? "已满足" : "未满足"),
    w: common_vendor.n($options.subtotal >= $data.shopInfo.min_order_amount ? "success" : "warning")
  } : {}, {
    x: common_vendor.t($options.totalAmount.toFixed(2)),
    y: common_vendor.t($options.totalAmount.toFixed(2)),
    z: common_vendor.o((...args) => $options.submitOrder && $options.submitOrder(...args)),
    A: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/order/create.js.map
