package api.data;


import com.vue.adminlte4j.model.DictEnum;

/**
 * Created by bjliuyong on 2018/5/17.
 */
public enum StatusEnum implements DictEnum {

    VALID(1, "有效") , IN_VALID(2, "无效") ;

    private int key  ;
    private String desc ;

    StatusEnum(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public int getKey() {
        return key;
    }

    public String code() {

        return this.key + "";
    }

    public String label() {
        return desc ;
    }


    public String getDesc() {
        return desc;
    }



}
