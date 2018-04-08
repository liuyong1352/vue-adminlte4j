package api.data.domain;

import com.vue.adminlte4j.annotation.DictData;
import com.vue.adminlte4j.annotation.DictEntry;
import com.vue.adminlte4j.annotation.Form;
import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.model.form.FormItemType;
import java.util.Date;

/**
 * Created by bjliuyong on 2018/3/30.
 */
@Form(span = 4 ,ignore = false)
public class XModel {

    private int age ;

    @UIFormItem(span = 3)
    private String name ;


    @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd hh:mm:ss") //使用fastjson 格式化
    private Date birthDay ;

    @UIFormItem(type = FormItemType.CHECKBOX)
    @DictData({@DictEntry("写作") ,@DictEntry("阅读"),@DictEntry("听说")})
    private String love ;

    @UIFormItem(type = FormItemType.RADIO)
    @DictData(value = {@DictEntry(code = "1",value ="男") ,@DictEntry(code = "2", value = "女"),@DictEntry(code = "3",value = "中性")})
    private int gender ;

    @UIFormItem(type = FormItemType.SWITCH , placeholder = "Y|N" , span = 12)
    private int isAdmin ;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
