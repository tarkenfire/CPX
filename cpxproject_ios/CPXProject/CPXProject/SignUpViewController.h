//
//  SignUpViewController.h
//  CPXProject
//
//  Created by Michael Mancuso on 3/27/14.
//  Copyright (c) 2014 Hinode Softworks. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SignUpViewController : UIViewController
{
    IBOutlet UITextField* usernameField;
    IBOutlet UITextField* passwordField;
    IBOutlet UITextField* passwordConfirmField;
}

-(IBAction)onClick:(id)sender;

@end
