package test.comparator.lambada;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ImperativeVsDeclarativeComparator {
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper()
				.setVisibility(PropertyAccessor.FIELD, Visibility.NONE)
				.setVisibility(PropertyAccessor.GETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.SETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.CREATOR, Visibility.PUBLIC_ONLY);
		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Student.class);

		File initialFile = new File("resources/persons.json");
		InputStream targetStream;
		try {
			targetStream = new FileInputStream(initialFile);
			List<Student> personDetails = mapper.readValue(targetStream, collectionType);
			personDetails.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}