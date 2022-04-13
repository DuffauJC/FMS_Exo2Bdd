package fr.fms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestJdbc {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub

		ArrayList<Article> articles =new ArrayList<Article>();

		// enregistre la class auprès du driver manager
		// (charge le pilote)
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// récupérer une connection à partir d'une url + id + pwd
		String url="jdbc:mariadb://localhost:3306/shop";
		String login="root";
		String password="fms2022";


		try(Connection connection=DriverManager.getConnection(url,login,password)){// connection de java sql
			String strSql="SELECT * FROM T_articles";						// une fois connecté, réalisation d'un requête
			try(Statement statement =connection.createStatement()){
				try(ResultSet resultSet=statement.executeQuery(strSql)){   // ResultSet de java.sql
					while (resultSet.next()) {
						int rsidArticle=resultSet.getInt(1);  // soit index(de 1 à n) de la colonne, soit le nom de la colonne
						String rsdescription =resultSet.getString(2);
						String rsbrand=resultSet.getString(3);
						double rsunitaryPrice=resultSet.getDouble(4);
						articles.add((new Article(rsidArticle,rsdescription,rsbrand,rsunitaryPrice)));
					}
				}
			}


			for (Article a : articles) {
				System.out.println(a.getIdArticle()+" - "+a.getDescription()+" - "+a.getBrand()+" - "+a.getUnitaryPrice());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

