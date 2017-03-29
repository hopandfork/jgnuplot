# How to contribute

The Hop and Fork dev team is spending a significant effort in trying to improve
and mantain jGNUplot, but third-party patches and bug reports are essential for
making it a first-class project, as it would be impossible for us to test it on
every conceivable platform or configuration.

In this short document we provide a few guidelines that we need contributors to
follow in order to work together in a crystal clear and straightforward way.
As you can see, this is not a list of strict rules: discouraging contributors is
the last thing we want!

## Getting Started

* Make sure you have a [GitHub account](https://github.com/signup/free)
* Create a GitHub issue, assuming one does not already exist.
  * Clearly describe the issue including steps to reproduce when it is a bug.
  * Make sure you fill in the earliest version that you know has the issue.
* Fork the repository on GitHub

## Making Changes

* Create a topic branch from where you want to base your work.
  * This is usually the master branch.
  * Only target release branches if you are certain your fix must be on that
    branch.
  * To quickly create a topic branch based on master; `git checkout -b
    fix/master/my_contribution master`. Please avoid working directly on the
    `master` branch.
  * The topic branch should be named after the number of the issue it fixes.
    The format is `jgp-n` where `n` is the issue number.  For minor changes
    (typically documentation-related) that have not an associated issue,
    following the format is not required: just keep the branch name significant.
* Make commits of logical units.
* Check for unnecessary whitespace with `git diff --check` before committing.
* Make sure your commit messages are in third person, and describe clearly the
  patch and the purpose for which the modifications have been made.
* Make sure you have added the necessary tests for your changes.
* Run _all_ the tests to assure nothing else was accidentally broken.

## Submitting Changes

* Push your changes to a topic branch in your fork of the repository.
* Submit a pull request to the repository in the hopandfork organization.
* The Hop and Fork team will process it as soon as possible!

## Revert Policy
By running tests in advance and by engaging with peer review for prospective
changes, your contributions have a high probability of becoming long lived
parts of the the project. After being merged, the code will run through a
series of tests in order to check for possible integration errors. 

If the code change results in a test failure, we will make our best effort to
correct the error. If a fix cannot be determined and committed within a short
time after its discovery, the commit(s) responsible _may_ be reverted, at the
discretion of the committer and jGNUplot  maintainers. This action would be
taken to help maintain passing states in our testing pipelines.

## Additional Resources

* [General GitHub documentation](https://help.github.com/)
* [GitHub pull request documentation](https://help.github.com/articles/creating-a-pull-request/)
* [Hop and Fork](https://www.hopandfork.org)
