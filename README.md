# Logger
Phone Assignment

1. How to use this?
  The logger application can be used by any client for logging purposes.
  Sample client is created to showcase the use of the application , by providing configuration map to logger.
  
 2. What features are supported?
  Currently the single threaded FILE sink logger is supported.
  User can provide namespace , configuration to logger to get the logger running.
  The Database logger has base structure implemented.
  The logger appends to the file in configuration till the max size is reached , and then rename the file and starts logging to new empty file.
  
 3. Future scope:
  Database sink implementation.
  Multithreaded logging.
  Sync/Async logging.
  
  
