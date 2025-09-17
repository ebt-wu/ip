# EGGY
# Features of Eggy Chatbot

Eggy Chatbot is a user-friendly personal assistant that helps organize and manage your daily tasks efficiently. Below are the main features currently supported:

## 📝 Task Management

- **Add Todo:**  
  Create a simple to-do with a task description.  
  Example: `todo Finish CS2103T assignment`

- **Add Deadline:**  
  Add tasks that have a specific due date and time.  
  Example: `deadline Submit AI project /by 2025-09-20 23:59`

- **Add Event:**  
  Schedule events that occur at a specific time.  
  Example: `event project meeting /from Mon 2pm /to 4pm`

## 📋 Viewing Tasks

- **List Command:**  
  Displays the list of all current tasks, showing their type (Todo, Deadline, Event), descriptions, statuses, and—for deadlines/events—their associated date and time.  
  Command: `list`

## ✅ Managing Tasks

- **Mark as Done:**  
  Mark a specific task as completed by its number in the list.  
  Example: `mark 2`

- **Unmark as Done:**
  Unmark a specific task as incomplete by its number in the list.
  Example: `unmark 2`

- **Delete Command:**  
  Delete a task from the list by its number.  
  Example: `delete 3`

## 🗂️ Sorting

- **Sort Deadlines by Date:**  
  Eggy can sort and show all deadline tasks ordered chronologically by their due date for better prioritization.  
  

## ❌ Command Examples

| Command                                      | Description                                         |
|----------------------------------------------|-----------------------------------------------------|
| `todo Buy groceries`                         | Adds a new Todo task                                |
| `deadline Submit report /by 2023-10-15T18:00`| Adds a Deadline with due date                       |
| `event project meeting /from Mon 2pm /to 4pm`| Adds an Event at specific time                      |
| `list`                                       | Lists all tasks                                     |
| `mark 2`                                     | Marks task 2 as completed                           |
| `delete 5`                                   | Deletes task 5 from the list                        |
| `unmark 2`                                   | Unmark the second task on the list                  |

---

Eggy Chatbot is designed to be simple, robust, and extensible—making it easy to manage tasks. For feedback or suggestions, feel free to contribute or open an issue!
