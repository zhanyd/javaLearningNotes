package zhan.foundation.lesson01;

public class Salary implements Comparable<Salary>{

	private String name;
	private int baseSalary;
	private int bonus;
	
	public Salary(String name,int baseSalary,int bonus){
		this.name = name;
		this.baseSalary = baseSalary;
		this.bonus = bonus;
	}
	
	public String toString(){
		return "name = " + name + " （baseSalary*13+bonus）=" + (baseSalary*13 + bonus);
	}
	
	@Override
	public int compareTo(Salary o){
		
		if(this.baseSalary*13 + this.bonus < o.baseSalary*13 + o.bonus){
			return 1;
		}
		if(this.baseSalary*13 + this.bonus == o.baseSalary*13 + o.bonus){
			return 0;
		}
		else{
			return -1;
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
}
