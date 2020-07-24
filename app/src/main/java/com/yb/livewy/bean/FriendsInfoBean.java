package com.yb.livewy.bean;

import java.io.Serializable;
import java.util.List;

public class FriendsInfoBean implements Serializable {


    /**
     * page : {"curPage":1,"total":1,"pageSize":100,"pageCount":1,"beginPos":0,"rows":[{"id":10,"name":null,"heamImg":null,"accid":"0617114054-9563","sex":null,"signature":null}]}
     * tname : 花漾客服群
     * tid : 2855362383
     * id : 2
     */

    private PageBean page;
    private String tname;
    private String tid;
    private int id;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class PageBean {
        /**
         * curPage : 1
         * total : 1
         * pageSize : 100
         * pageCount : 1
         * beginPos : 0
         * rows : [{"id":10,"name":null,"heamImg":null,"accid":"0617114054-9563","sex":null,"signature":null}]
         */

        private int curPage;
        private int total;
        private int pageSize;
        private int pageCount;
        private int beginPos;
        private List<RowsBean> rows;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getBeginPos() {
            return beginPos;
        }

        public void setBeginPos(int beginPos) {
            this.beginPos = beginPos;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * id : 10
             * name : null
             * heamImg : null
             * accid : 0617114054-9563
             * sex : null
             * signature : null
             */

            private int id;
            private String name;
            private String heamImg;
            private String accid;
            private int sex;
            private String signature;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHeamImg() {
                return heamImg;
            }

            public void setHeamImg(String heamImg) {
                this.heamImg = heamImg;
            }

            public String getAccid() {
                return accid;
            }

            public void setAccid(String accid) {
                this.accid = accid;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }
        }
    }
}
