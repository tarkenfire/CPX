//
//  SignUpViewController.m
//  CPXProject
//
//  Created by Michael Mancuso on 3/27/14.
//  Copyright (c) 2014 Hinode Softworks. All rights reserved.
//

#import <Parse/Parse.h>
#import "SignUpViewController.h"
#import "MainListViewController.h"

@interface SignUpViewController ()

@end

@implementation SignUpViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"Sign Up";
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)onClick:(id)sender
{
    //check password matches
    if ([[passwordField text] isEqualToString:[passwordConfirmField text]])
    {
        PFUser *user = [PFUser user];
        user.username = [usernameField text];
        user.password = [passwordField text];
        
        [user signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
            if (!error)
            {
                MainListViewController* vc = [[MainListViewController alloc] initWithNibName:@"MainListViewController" bundle:nil];
                [self.navigationController pushViewController:vc animated:true];
            }
            else
            {
                NSString *errorString = [error userInfo][@"error"];
                
                UIAlertView* alert = [[UIAlertView alloc] initWithTitle:@"Sign Up Error"
                                                                message:errorString
                                                               delegate:nil
                                                      cancelButtonTitle:@"OK"
                                                      otherButtonTitles:nil];
                [alert show];

            }
        }];
    }
    else
    {
        UIAlertView* alert = [[UIAlertView alloc] initWithTitle:@"Password Mismatch"
                                                        message:@"Passwords do not match."
                                                       delegate:nil
                                              cancelButtonTitle:@"OK"
                                              otherButtonTitles:nil];
        [alert show];
    }
    
}

@end
