package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the gas_price database table.
 * 
 */
@Entity
@Table(name="gas_price")
@NamedQuery(name="GasPrice.findAll", query="SELECT g FROM GasPrice g")
public class GasPrice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="gas_price_id")
	private String gasPriceId;

	@Column(name="gas_price")
	private double gasPrice;

	@Column(name="gas_price_time")
	private Timestamp gasPriceTime;

	@Column(name="gas_type")
	private String gasType;

	public GasPrice() {
	}

	public String getGasPriceId() {
		return this.gasPriceId;
	}

	public void setGasPriceId(String gasPriceId) {
		this.gasPriceId = gasPriceId;
	}

	public double getGasPrice() {
		return this.gasPrice;
	}

	public void setGasPrice(double gasPrice) {
		this.gasPrice = gasPrice;
	}

	public Timestamp getGasPriceTime() {
		return this.gasPriceTime;
	}

	public void setGasPriceTime(Timestamp gasPriceTime) {
		this.gasPriceTime = gasPriceTime;
	}

	public String getGasType() {
		return this.gasType;
	}

	public void setGasType(String gasType) {
		this.gasType = gasType;
	}

}