package api.data.domain;

import com.vue.adminlte4j.annotation.Form;
import com.vue.adminlte4j.annotation.UIDate;
import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.model.form.DateType;

/**
 * Created by bjliuyong on 2018/4/9.
 */
@Form(inline = true  , hidden = false , span = 2 , ignore = false , ref = XModel.class)
public class XModelQuery  {

    private String name ;

    private int age ;

    @UIFormItem(label = "")
    private int maxAge ;

    private String love ;

    @UIFormItem
    @UIDate(type = DateType.DATE , range = true)
    private String birthDay ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
