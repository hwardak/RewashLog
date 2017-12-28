# RewashLog
Logging tool for issued rewashes, replacing rewash binder.

Developed for gas stations with carwashes. To keep track of re-issued carwash codes.
Carwash codes are issued and given to the customer upon purchase of their desired wash package.
If the carwash malfunctions, or the customer is unhappy with the results of the wash, either a refund or another wash code is to be issued.
Replacement wash codes must be kept track of for two reasons; Firstly to monitor and detect fraudulent activity such as employees taking wash codes under false pretense. Secondly, to monitor and track carwash quality and possible maintenance requirements. For example, seeing multiple rewash codes under the "Did not dry properly" reason should be an indication that the dryers need an inspection/maintenance. 

This application is to run on a location specific, dedicated Android device. 
Users are to create their account via the Employees activity. Once their account is setup, they can start logging rewashes. Users can be viewed and deleted from within the Employees activity, accessed via the Employees button in the main activity .
Users can start the logging process by entering their user ID in the only EditText in the Main activity. 
The date, time and employee name of the rewash entry is automatically set. They are then to select the wash-package being issued (low, mid, and high). Once the wash package is selected, the option to select a "Reason" for the rewash is made visible. Reasons include; "Not clean", "No soap", "Left over soap", "Did not dry properly", "Expired code", "Customer satisfaction", "Test wash", and "Administrative". Once a reason is selected the Save button is made visible and the Rewash can be logged. The user then will be returned the user back to the main activity.

Rewash logs can be viewed by clicking the Rewash Log button in Main Activity. By default, all logged rewashes will be displayed in a scrollable ListView. Users can narrow the list of rewashes by month and year via the month and year spinners at the top of the activity. Counts of each wash-package and total rewashes are also displayed here.
From within the Rewash Log activity, users can also export the displayed Rewash Log in a text file format to an email of their choice. This is a particularly useful feature as many franchisee and their head offices require a hard copy for each month.

To be able to export the rewash log, users will need a GMail email address. The GMail server is used to send out the rewash log. This can be set within the Settings activity, accessed from the main activity via the Settings button. 
Within the settings activity the user will need to provide their GMail email address, the password for it (stored on device and encrypted), their default destination email for where the rewash log should be sent (user can provide an alternate destination email address when exporting from the Rewash Log activity), and their location identification number.

Happy rewash logging!

-Hasib Wardak
-2017


