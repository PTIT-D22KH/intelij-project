package duongvct.app.utls;

import java.util.List;

public class PaginatedResponse<T>{
    private Meta meta;
    private List<T> result;
    public PaginatedResponse(List<T> result, int current, int pageSize, int pages, long total) {
        this.meta = new Meta(current + 1, pageSize, pages, total);
        this.result = result;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public static class Meta {
        private int current;
        private int pageSize;
        private int pages;
        private long total;

        public Meta(int current, int pageSize, int pages, long total) {
            this.current = current;
            this.pageSize = pageSize;
            this.pages = pages;
            this.total = total;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }
    }
}
