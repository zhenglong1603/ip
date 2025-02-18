package zbot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import zbot.tasks.TaskList;

class ZbotTest {
    // This code is adapted from a conversation with chatGPT
    private Zbot zbot;
    private StorageManager storage;
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        String filePath = "test_data.txt";
        storage = new StorageManager(filePath);

        // Initialize with a known state for the TaskList
        taskList = new TaskList();

        // Create Zbot instance
        zbot = new Zbot(filePath);
    }

    @Test
    void testGetResponseWithValidInput() throws Exception {
        // Setup valid command
        String input = "todo Buy groceries";

        // Expected response from the Parser (this can depend on the actual implementation)
        String expectedResponse = "Got it! Another task added to your plate!\n";

        // Call the method and assert
        String actualResponse = zbot.getResponse(input);
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    void testGetResponseWithInvalidCommand() throws Exception {
        // Setup invalid command
        String input = "invalid command";

        // Call the method and assert that an InvalidCommandException is caught
        String actualResponse = zbot.getResponse(input);
        assertTrue(actualResponse.contains("Sorry!! That command flew right over my head."));
    }

    @Test
    void testGetResponseWithIoException() throws IOException {
        // Setup input that should trigger an IOException
        String input = "load tasks";

        // Force an IOException by having the StorageManager throw it
        StorageManager faultyStorage = new StorageManager("non_existing_file.txt");

        Zbot faultyZbot = new Zbot("non_existing_file.txt");

        // Call the method and check the error handling for IOException
        String actualResponse = faultyZbot.getResponse(input);
        assertTrue(actualResponse.contains("Sorry!!"));
    }
}
