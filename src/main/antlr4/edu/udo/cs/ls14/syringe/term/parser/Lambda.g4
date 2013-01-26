grammar Lambda;

lambda      :   abstractionLevel;

variableLevel       :   ID                               # variable
                    |   '(' abstractionLevel ')'         # parenthesis
                    ;

applicationLevel    :   variableLevel variableLevel*     # application
                    ;

abstractionLevel    :   '\\' ID+ '->' applicationLevel   # abstraction
                    |   applicationLevel                 # passDown
                    ;

ID          :   [a-zA-Z]([a-zA-Z0-9]*);
WHITESPACE  :   [ \t\n\r]   ->  skip;