# RewashLog
Logging tool for issued rewashes, replacing rewash binder.

Developed for gas stations with carwashes. To keep track of re-issued carwash codes.
Carwash codes are issued and given to the customer upon purchase of their desired wash package.
If the carwash malfunctions, or the customer is unhappy with the results of the wash, either a refund or another wash code is to be issued.
Replacement wash codes must be kept track of for two reasons; Firstly to monitor and detect fruadulent activity such as employees taking washcodes under false pretense. Secondly to monitor and track carwash quality and possible neccesary maintenance. For example, seeing multple rewash codes under the "Did not dry properly" reason should be an indication that the dryers need a inspection/maintenance. 

This application is to run on a location specific, dedicated Android device. 
Users are to create their account via the Employees activity. Once their account is setup, they start logging rewashes. 
Users can start the logging process by entering their user ID in the only EditText in the Main activity. 
The date and time of the rewash is automatically set. They are then to select the wash-package being issued (low, mid, and high). Once the wash package is selected, the option to select a "Reason" for the rewash is made visible. Reasons include; "Not clean", "No soap", "Left over soap", "Did not dry properly", "Expired code", "Customer satisfaction", "Test wash", and "Administrative".

