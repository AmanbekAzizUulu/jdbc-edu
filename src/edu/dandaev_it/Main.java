package edu.dandaev_it;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) {
		getImage();

	}

	private static void saveImage() throws SQLException, IOException {
		String insertImageIntoAircraft = """
				update
				  aircraft
				set
				  aircraft_image = ?
				where
				  id = ? ;
				""";

		try (var connection = ConnectionManager.open();
				var preparedStatement = connection.prepareStatement(insertImageIntoAircraft)) {

			Blob image = connection.createBlob();
			image.setBytes(1, Files.readAllBytes(Path.of("resources/media/Cathay_Pacific_Boeing_777-200.jpg")));

			preparedStatement.setBlob(1, image);
			preparedStatement.executeUpdate();
			preparedStatement.setInt(2, 1);
		}
	}

	private static void getImage() {
		String select_image_from_database = """
				select
				  aircraft_image
				from
				  aircraft
				where
				  id = ?
				""";
		try (Connection connection = ConnectionManager.open();
				PreparedStatement preparedStatement = connection.prepareStatement(select_image_from_database)) {

			preparedStatement.setInt(1, 1);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					Blob blob = resultSet.getBlob("aircraft_image");
					InputStream inputStream = blob.getBinaryStream();

					Files.write(Path.of("resources", "boeing777-200_new.jpg"), inputStream.readAllBytes(),
							StandardOpenOption.CREATE);

					// Переводим указатель на начальную точку
					inputStream.reset();

					// Преобразование в BufferedImage
					BufferedImage image = ImageIO.read(inputStream);

					// Создание и отображение JFrame с изображением
					JFrame frame = new JFrame();
					JLabel label = new JLabel(new ImageIcon(image));
					frame.add(label);
					frame.pack();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setTitle("Image from Database");
					frame.setVisible(true);
				}
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

	}
}
