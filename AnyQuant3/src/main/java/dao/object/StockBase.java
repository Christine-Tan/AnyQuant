package dao.object;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by JiachenWang on 2016/5/12.
 */

@Entity
@Table(name = "stock_list", catalog = "anyquant")
public class StockBase implements Serializable {
    /**
     * 股票编号
     */
    private String id;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 所属行业名称
     */
    private String industry;

    @Id
    @Column(name="ID")
    public String getId() {
        return id;
    }

    @Column(name="industry")
    public String getIndustry() {
        return industry;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

}
