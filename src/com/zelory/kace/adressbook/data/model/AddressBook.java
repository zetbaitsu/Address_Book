package com.zelory.kace.adressbook.data.model;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zelory.kace.adressbook.util.JsonParser;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddressBook {
    private String name;
    private List<Person> persons;
    private String path;

    public AddressBook() {
        this("Untitled", new ArrayList<>());
    }

    public AddressBook(File file) throws IOException, JsonSyntaxException {
        StringBuilder data = new StringBuilder();
        Files.lines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8).forEach(data::append);
        name = file.getName();
        persons = JsonParser.getInstance().getParser().fromJson(data.toString(), new TypeToken<List<Person>>() {}.getType());
        path = file.getAbsolutePath();
    }

    public AddressBook(String name, List<Person> persons) {
        this.name = name;
        this.persons = persons;
    }
}
