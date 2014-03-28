//
//  LogInViewController.m
//  CPXProject
//
//  Created by Michael Mancuso on 3/27/14.
//  Copyright (c) 2014 Hinode Softworks. All rights reserved.
//

#import "LogInViewController.h"
#import <Parse/Parse.h>
#import "MainListViewController.h"

@interface LogInViewController ()

@end

@implementation LogInViewController

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
    self.title = @"Log In";
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)onClick:(id)sender
{
    
    [PFUser logInWithUsernameInBackground:[usernameField text] password:[passwordField text]
                                    block:^(PFUser *user, NSError *error)
                                    {
                                        if (user)
                                        {
                                            
                                            MainListViewController* vc = [[MainListViewController alloc] initWithNibName:@"MainListViewController" bundle:nil];
                                            [self.navigationController pushViewController:vc animated:true];
                                            
                                        }
                                        else
                                        {
                                            UIAlertView* alert = [[UIAlertView alloc] initWithTitle:@"Login Failure"
                                                                                            message:@"Login Failed. Check password and try again."
                                                                                           delegate:nil
                                                                                  cancelButtonTitle:@"OK"
                                                                                  otherButtonTitles:nil];
                                            [alert show];

                                        }
                                    }];

}



@end
