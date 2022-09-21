package com.work.ssj.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author wjning
 * @date 2020/11/11 9:51
 * @description 自定义BigDecimal工具栏，用来计算财务部门
 */
public class DecUtil{

    private BigDecimal  decValue;

    private DecUtil(){
    }

    /**
     * 获取值
     */
    public BigDecimal value(){
        return this.decValue;
    }

    public static DecUtil of(){
        DecUtil bigDecimalUtil = new DecUtil();
        bigDecimalUtil.decValue = BigDecimal.ZERO;
        return bigDecimalUtil;
    }

    public static DecUtil of(BigDecimal deValue){
        if (deValue == null) {
            return of();
        }
        DecUtil bigDecimalUtil = new DecUtil();
        bigDecimalUtil.decValue = deValue;
        return bigDecimalUtil;
    }

    /**
     * 除
     */
    public DecUtil withDiv(BigDecimal b,int scale){
        if (b == null || b.compareTo(BigDecimal.ZERO) == 0) {
            this.decValue = BigDecimal.ZERO;
        }else {
            this.decValue = this.decValue.divide(b, scale, RoundingMode.HALF_UP);
        }
        return this;
    }

    /**
     * 除
     */
    public DecUtil withDiv(BigDecimal b){
        if (b == null || b.compareTo(BigDecimal.ZERO) == 0) {
            this.decValue = BigDecimal.ZERO;
        }else {
            this.decValue = this.decValue.divide(b, RoundingMode.HALF_UP);
        }
        return this;
    }

    /**
     * 加
     */
    public DecUtil withAdd(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return this;
        }
        for (BigDecimal value : values) {
            if (null != value) {
                this.decValue = this.decValue.add(value);
            }
        }
        return this;
    }

    /**
     * 减
     */
    public DecUtil withSub(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return this;
        }
        for (BigDecimal value : values) {
            if (null != value) {
                this.decValue = this.decValue.subtract(value);
            }
        }
        return this;
    }

    /**
     * 乘
     */
    public DecUtil withMul(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            this.decValue = BigDecimal.ZERO;
            return this;
        }
        for (BigDecimal value : values) {
            if (null != value) {
                this.decValue = this.decValue.multiply(value);
            }else {
                this.decValue = BigDecimal.ZERO;
            }
        }
        return this;
    }

    /**
     * 乘
     */
    public DecUtil withMul(int scale,BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            this.decValue = BigDecimal.ZERO;
            return this;
        }
        for (BigDecimal value : values) {
            if (null != value) {
                this.decValue = this.decValue.multiply(value).setScale(scale, RoundingMode.HALF_UP);
            }else {
                this.decValue = BigDecimal.ZERO;
            }
        }
        return this;
    }

    public static boolean isZero(BigDecimal number){
        if (number == null) return true;
        return number.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 将string类型转换为BigDecimal
     */
    public static BigDecimal init(String number){
        if (StrUtil.isEmpty(number) || StrUtil.isBlank(number)) {
            return BigDecimal.ZERO;
        }
        if(number.contains(",")){
            number = number.replace(",","");
        }
        if (!NumberUtil.isNumber(number.trim())) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(number.trim());
    }
    /**
     * 初始化
     */
    public static BigDecimal init(){
            return BigDecimal.ZERO;
    }

    public static BigDecimal init(BigDecimal number){
        return number == null ? init() : number;
    }

    /**
     * 自定义除法
     */
    public static BigDecimal div(BigDecimal b1,BigDecimal b2,int scale){

        if (b1 == null || b2 == null) {
            return BigDecimal.ZERO;
        }
        if (b1.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        if (b2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return b1.divide(b2,scale, RoundingMode.HALF_UP);
    }

    /**
     * 除
     */
    public static BigDecimal div(BigDecimal b1,BigDecimal b2){
        return div(b1, b2, 2);
    }

    /**
     * 加
     */
    public static BigDecimal add(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }
        BigDecimal value = values[0];
        BigDecimal result = null == value ? BigDecimal.ZERO : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(value);
            }
        }
        return result;
    }

    public static BigDecimal add(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        String value = values[0];
        BigDecimal result = null == value ? BigDecimal.ZERO : new BigDecimal(value).setScale(3, RoundingMode.HALF_UP);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(new BigDecimal(value).setScale(3, RoundingMode.HALF_UP));
            }
        }
        return result;
    }

    /**
     * 减
     */
    public static BigDecimal sub(String... values){
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        String value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(new BigDecimal(value));
            }
        }
        return result;
    }

    /**
     * 减
     */
    public static BigDecimal sub(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal value = values[0];
        BigDecimal result = null == value ? BigDecimal.ZERO : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(value);
            }
        }
        return result;
    }

    /**
     * 乘
     */
    public static BigDecimal mul(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal value = values[0];
        BigDecimal result = null == value ? BigDecimal.ZERO : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘
     */
    public static BigDecimal mul(int scale,BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal value = values[0];
        BigDecimal result = null == value ? BigDecimal.ZERO : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.multiply(value).setScale(scale,RoundingMode.HALF_UP);
            }
        }
        return result;
    }
}
