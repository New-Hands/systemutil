package com.lstfight.systemutil.log;

/**
 * @author 李尚庭
 * @date 2018/8/29 0029 16:49
 */
public class Constant {
    public enum Modle {
        /**
         * 标定审核模块
         */
        BDSH("BDSH", "标定审核"),;

        private String code;
        private String Name;

        Modle(String code, String name) {
            this.code = code;
            Name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return Name;
        }

    }


}
