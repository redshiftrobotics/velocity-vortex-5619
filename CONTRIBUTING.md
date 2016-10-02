# Contributing

* If you make changes, please make your changes on a branch, then open a pull-request.
* Code Reviews are ***REQUIRED***, on all pull-requests. (This is also enforced by Github.)
    * The goal of this is that `master` is ***always*** working. It should always be safe to fork
      master, and if you fork master and break something, it should always be your fault.
    * This is called [Github Flow][]
* All PRs should mention the issue(s) that they address, preferably in one of the [magic formats][].
* Every task on the SCRUM board in the STREAM basement should also have a corresponding GH Issue.
  This issue number should be written on the paper task. The status of the task should also match
  the state of the PR. (ex. Open = Backlog, `in progress` label = In Progress, Closed = Complete)
    * Please make sure to tag your issues!
    * As soon as someone is assigned on the SCRUM board, they should be assigned in Github.
    * Please put your issues in the appropriate milestone!
	* Feel free to open issues for really, really far-off features. Just give them a future milestone.
    * Just generally keep GH Issues in sync with the SCRUM Board.
* Commit early, Commit often.
* Commit guidelines! Commits should always be written in the second person imperative, and with no period
  at the end (?, ! and &#8253; are OK though). This means that your commit message should tell anyone
  looking at it _what the commit will do to the codebase_. You should also read [this guide on git commit messages](http://chris.beams.io/posts/git-commit/),
  and follow it.
    * Good:
	* `Add laser cannons`
	* `Add laser cannons!`
        * `Fix the laser cannons`
    * Bad:
	* `add laser cannons.`
	* `ADD LASER CANNONS`
	* `aDD LASER CANNONS`
	* `addition: laser cannons`
	* `added the laser cannons`
	* `I added the laser cannons`
	* `We added the laser cannons`
	* `Noah added the laser cannons`
	* `Will add the laser cannons`
	* `commit message`
	* `changed a thing`
	* `fixed it`
	* `<crickets>`
	* etc.
    * I ([@ariporad][]) personally use the [angular.js commit message guidelines][ang-cmg], and you
      should too! But I won't force you. For the record, It's basically:
      `<feat|fix|perf|doc|style|test>[(scope)]: message`. If you do this, than you are exempt from the rule about capitalization. For example:
	    * `feat(lasers): add laser cannons`
	    * `fix: don't crash on lunch.`
	    * etc.


[this guide on git commit messages]: http://chris.beams.io/posts/git-commit/ "Or Else"
[Github Flow]: https://guides.github.com/introduction/flow/ "Github Flow"
[@ariporad]: https://github.com/ariporad "@ariporad"
[ang-cmg]: https://github.com/angular/angular.js/blob/master/CONTRIBUTING.md#commit
[magic formats]: https://github.com/blog/1506-closing-issues-via-pull-requests
