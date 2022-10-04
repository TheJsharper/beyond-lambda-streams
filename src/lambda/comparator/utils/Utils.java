package lambda.comparator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import lambda.comparator.lambda.model.Student;

public class Utils {

	public static List<Student> createStudentList() {
		ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.NONE)
				.setVisibility(PropertyAccessor.GETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.SETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.CREATOR, Visibility.PUBLIC_ONLY);
		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Student.class);

		File initialFile = new File("resources/persons.json");
		List<Student> students = new ArrayList<>();
		try {
			InputStream targetStream = new FileInputStream(initialFile);
			students = mapper.readValue(targetStream, collectionType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return students;
	}
}
