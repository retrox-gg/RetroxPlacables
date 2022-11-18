package xyz.larkyy.retroxplacables.model;

import java.util.List;

public interface ModelFactory {

    Model create(List<String> args);

}
